
package com.cityway.activities.business.services;

import java.util.List;
import java.util.UUID;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;

/**
 * The Interface ActivityService.
 */
public interface ActivityService {
	
	
	Activity create (Activity activity);	//CREATE C
	Activity read (UUID id);				//READ	 R
	boolean update (Activity activity);		//UPDATE U
	boolean delete (UUID id);				//DELETE D
	boolean delete (Activity activity);
	
	List<Activity> getAll();
	List<Activity> getByCategory(Category category);
	List<Activity> getByCity(String city);
	List<Activity> getBetweenPriceRange(double min, double max);
	List<Activity> getByAdminPetsTrue();
	List<Activity> getByWheelchairAcessibleTrue();
	List<Activity> getByLanguaguesContaining(String languague);
	
	long getTotalNumberOfActivities();
	long getTotalNumberOfActivitiesByCategory(Category category);
	long getTotalNumberOfActivitiesByCity(String city);
}
