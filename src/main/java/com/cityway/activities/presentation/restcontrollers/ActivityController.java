package com.cityway.activities.presentation.restcontrollers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
					@Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") }),

			@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
					@Content(schema = @Schema()) }) })
	public ResponseEntity<?> get(
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String country, 
			@RequestParam(required = false) Category category,
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) String language,
			@RequestParam(required = false, defaultValue = "0") Double minPrice, 
			@RequestParam(required = false) Double maxPrice,
			@RequestParam(required = false) String date, 
			@RequestParam(required = false, defaultValue = "false") boolean wheelchairAccessible,
			@RequestParam(required = false, defaultValue = "false") boolean adminPets) {

		List<Activity> listActivities = activityService.getByParams(name, category, minPrice, maxPrice, city, country, language, date, adminPets, wheelchairAccessible);	
		return getResponse(listActivities);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get an activity as per the id", description = "Returns an activity as per the id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
					@Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
					@Content(schema = @Schema()) }) })
	public ResponseEntity<?> getById(@PathVariable String id) {
		Activity activity = activityService.read(id);

		if (activity == null) {
			throw new ActivityNotFoundException(id);
		}

		activity = addSelfLink(activity);
		return ResponseEntity.ok(activity);
	}

	@PostMapping
	@Operation(summary = "Create a new activity", description = "Returns the activity updated")
	@ApiResponse(responseCode = "201", description = "Successfully created", content = {
			@Content(schema = @Schema(), mediaType = "application/json") })
	public ResponseEntity<?> create(@RequestBody Activity activity, UriComponentsBuilder uri) {
		activityService.create(activity);
		return ResponseEntity.created(uri.path("/activities/{id}").build(activity.getId())).build();

	}

	@DeleteMapping
	@Operation(summary = "Delete the activity passed as parameter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successfully deleted", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
					@Content(schema = @Schema()) }) })
	public ResponseEntity<?> delete(@RequestBody Activity activity) {
		activityService.delete(activity);
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete the activity as per the id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successfully deleted", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
					@Content(schema = @Schema()) }) })
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		activityService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	@Operation(summary = "Update the activity passed as parameter", description = "Returns the activity updated")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated", content = {
					@Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
					@Content(schema = @Schema()) }) })
	public ResponseEntity<?> update(@RequestBody Activity activity) {
		activityService.update(activity);
		return ResponseEntity.ok(activity);
	}

	@PatchMapping("/{id}/{imageName}")
	@Operation(summary = "Update the image of the activity with one of the images of the gallery", description = "Returns the activity updated")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated", content = {
					@Content(schema = @Schema(), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
					@Content(schema = @Schema()) }) })

	public ResponseEntity<?> updateImage(@PathVariable String id, @PathVariable String imageName) {
		activityService.updateImage(id, imageName);
		return this.getById(id);
	}

	// ***************************************************************
	//
	// PRIVATE METHODS
	//
	// ***************************************************************

	private ResponseEntity<?> getResponse(List<Activity> list) {
		return list.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(addingSelfReferences(list));
	}

	private Activity addSelfLink(Activity activity) {
		return activity.add(linkTo(ActivityController.class).slash(activity.getId()).withSelfRel());
	}

	private List<Activity> addingSelfReferences(List<Activity> activities) {
		return activities.stream().map(this::addSelfLink).toList();
	}

}
