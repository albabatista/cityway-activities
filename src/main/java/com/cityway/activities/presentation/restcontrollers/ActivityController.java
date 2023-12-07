package com.cityway.activities.presentation.restcontrollers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

@RestController
@CrossOrigin
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping
	public ResponseEntity<?> get( @RequestParam (required = false) String city,
									 @RequestParam (required = false) Category category,
									 @RequestParam (required = false) String name,
									 @RequestParam (required = false) String language,
									 @RequestParam (required = false, defaultValue = "0") Double min,
									 @RequestParam (required = false) Double max,
									 @RequestParam(required=false) @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate date,
									 @RequestParam (required = false) Boolean wheelchairAcessible,
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
		
		}else if (Boolean.TRUE.equals(wheelchairAcessible)) {
			listActivities = activityService.getByWheelchairAcessibleTrue();
		}
		
		return getResponse(listActivities);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable UUID id) {
		Activity activity = activityService.read(id);

		if (activity == null) {
			throw new ActivityNotFoundException(id);
		}

		return ResponseEntity.ok(activity);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Activity activity, UriComponentsBuilder uri) {
		activityService.create(activity);

		return ResponseEntity.created(uri.path("/activities/{id}").build(activity.getId())).build();

	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody Activity activity) {

		if (activity == null || activityService.read(activity.getId()) == null) {
			throw new ActivityNotFoundException(activity);
		}

		activityService.delete(activity);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable UUID id) {

		if (activityService.read(id) == null) {
			throw new ActivityNotFoundException(id);
		}

		activityService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Activity activity){
		
		UUID id = activity.getId();
		
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