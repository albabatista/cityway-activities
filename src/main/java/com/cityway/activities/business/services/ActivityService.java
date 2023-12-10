
package com.cityway.activities.business.services;

import java.util.List;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;

/**
 * The Interface ActivityService.
 */
public interface ActivityService {
	
	
	void create (Activity activity);
	Activity read (String id);	
	void update (Activity activity);
	void delete (String id);
	void delete (Activity activity);
	
	List<Activity> getAll();
	List<Activity> getByNameContaining(String name);
	List<Activity> getByCategory(Category category);
	List<Activity> getByCity(String city);
	List<Activity> getByPriceBetween(double min, double max);
	List<Activity> getByAdminPetsTrue();
	List<Activity> getByWheelchairAccessibleTrue();
	List<Activity> getByLanguaguesContaining(String languague);
	List<Activity> getByDateAvailable(String date);
	
	long getTotalNumberOfActivities();
	long getTotalNumberOfActivitiesByCategory(Category category);
	long getTotalNumberOfActivitiesByCity(String city);
}
