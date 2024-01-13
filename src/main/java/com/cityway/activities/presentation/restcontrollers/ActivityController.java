package com.cityway.activities.presentation.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.business.services.ActivityService;
import com.cityway.activities.presentation.exceptions.ActivityNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("/activities")
@Tag(name = "Activities API")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping
	@Operation(summary = "Get the activities", description = "Returns the activities")
	@ApiResponse(responseCode = "200", description= "Successfully retrieved", content = { @Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") })
	@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = { @Content(schema = @Schema()) })
	public ResponseEntity<?> get( @RequestParam (required = false) String city,
									 @RequestParam (required = false) Category category,
									 @RequestParam (required = false) String name,
									 @RequestParam (required = false) String language,
									 @RequestParam (required = false, defaultValue = "0") Double min,
									 @RequestParam (required = false) Double max,
									 @RequestParam(required=false)  String date,
									 @RequestParam (required = false) Boolean wheelchairAccessible,
									 @RequestParam (required = false) Boolean adminPets) {
		
		List<Activity> listActivities = activityService.getAll();
		
		if (city != null) {
			listActivities = activityService.getByCity(city);
		
		}else if (category != null) {
			listActivities = activityService.getByCategory(category);
		
		}else if (name != null) {
			listActivities = activityService.getByNameContaining(name);
		
		}else if (language != null) {
			listActivities = activityService.getByLanguaguesContaining(language);
		
		}else if (date != null) {
			listActivities = activityService.getByDateAvailable(date);
		
		}else if (min != null && max !=null) {
			listActivities = activityService.getByPriceBetween(min, max);
		
		}else if (Boolean.TRUE.equals(adminPets)) {
			listActivities = activityService.getByAdminPetsTrue();
		
		}else if (Boolean.TRUE.equals(wheelchairAccessible)) {
			listActivities = activityService.getByWheelchairAccessibleTrue();
		}
		
		return getResponse(listActivities);
	}

	@Operation(summary = "Get an activity as per the id", description = "Returns an activity as per the id")
	@ApiResponse(responseCode = "200", description= "Successfully retrieved", content = { @Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") })
	@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = { @Content(schema = @Schema()) })
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) {
		Activity activity = activityService.read(id);

		if (activity == null) {
			throw new ActivityNotFoundException(id);
		}

		return ResponseEntity.ok(activity);
	}

	@Operation(summary = "Create a new activity", description = "Returns the link to the new activity")
	@ApiResponse(responseCode = "201", description= "Successfully created", 
				content = { @Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") })
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Activity activity, UriComponentsBuilder uri) {
		activityService.create(activity);

		return ResponseEntity.created(uri.path("/activities/{id}").build(activity.getId())).build();

	}
	
	@DeleteMapping
	@Operation(summary = "Delete the activity passed as parameter")
	@ApiResponse(responseCode = "204", description = "Successfully deleted", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = { @Content(schema = @Schema()) })
	public ResponseEntity<?> delete(@RequestBody Activity activity) {
		
		if ((activity != null) && (activityService.read(activity.getId()) != null)) {
			activityService.delete(activity);
			return ResponseEntity.noContent().build();
		}

		throw new ActivityNotFoundException(activity);		
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete the activity as per the id")
	@ApiResponse(responseCode = "204", description = "Successfully deleted", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = { @Content(schema = @Schema()) })
	public ResponseEntity<?> deleteById(@PathVariable String id) {

		if (id.isBlank() || activityService.read(id) == null) {
			throw new ActivityNotFoundException(id);
		}

		activityService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	@Operation(summary = "Update the activity passed as parameter", description = "Returns the activity updated")
	@ApiResponse(responseCode = "200", description= "Successfully updated", 
				content = { @Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") })
	@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = { @Content(schema = @Schema()) })
	public ResponseEntity<?> update(@RequestBody Activity activity){
		
		String id = activity.getId();
		
		if(activityService.read(id)== null){
			throw new ActivityNotFoundException(id);
		}
		
		activityService.update(activity);
		return ResponseEntity.ok(activity);
	}	

	private ResponseEntity<?> getResponse(List<Activity> list) {
		return list.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(list);
	}

}
