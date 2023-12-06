
package com.cityway.activities.business.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;

/**
 * The Interface ActivityService.
 */
public interface ActivityService {
	
	
	void create (Activity activity);
	Activity read (UUID id);	
	void update (Activity activity);
	void delete (UUID id);
	void delete (Activity activity);
	
	List<Activity> getAll();
	List<Activity> getByNameContaining(String name);
	List<Activity> getByCategory(Category category);
	List<Activity> getByCity(String city);
	List<Activity> getByPriceBetween(double min, double max);
	List<Activity> getByAdminPetsTrue();
	List<Activity> getByWheelchairAcessibleTrue();
	List<Activity> getByLanguaguesContaining(String languague);
	List<Activity> getByDateAvailable(LocalDate date);
	
	long getTotalNumberOfActivities();
	long getTotalNumberOfActivitiesByCategory(Category category);
	long getTotalNumberOfActivitiesByCity(String city);
}
