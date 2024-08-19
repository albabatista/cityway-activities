
package com.cityway.activities.business.services;

import java.util.List;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;

/**
 * The Interface ActivityService.
 */
public interface ActivityService {

	void create(Activity activity);

	Activity read(String id);

	void update(Activity activity);

	void delete(String id);

	void delete(Activity activity);

	void updateImage(String id, String imageName);

	List<Activity> getByParams(String name, Category category, Double minPrice, Double maxPrice, String city,
			String country, String language, String date, boolean adminPets, boolean wheelchairAccessible);

}
