package com.cityway.activities.integration.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;

/**
 * The Interface CustomActivityRepository.
 */
@Repository
public interface CustomActivityRepository {

	/**
	 * Find by params.
	 *
	 * @param name                 the name
	 * @param category             the category
	 * @param minPrice             the min price
	 * @param maxPrice             the max price
	 * @param city                 the city
	 * @param country              the country
	 * @param language             the language
	 * @param date                 the date
	 * @param adminPets            the admin pets
	 * @param wheelchairAccessible the wheelchair accessible
	 * @return the list
	 */
	List<ActivityDto> findByParams(String name, CategoryDto category, Double minPrice, Double maxPrice, String city,
			String country, String language, String date, boolean adminPets, boolean wheelchairAccessible);
}
