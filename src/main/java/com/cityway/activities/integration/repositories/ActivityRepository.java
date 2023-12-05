package com.cityway.activities.integration.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.integration.models.ActivityDto;

/**
 * The Interface ActivityRepository.
 */
@Repository
public interface ActivityRepository extends MongoRepository<ActivityDto, UUID> {
	
	List<Activity> findByCategory(Category category);
	List<Activity> findByCity(String city);
	List<Activity> findBetweenPriceRange(double min, double max);
	List<Activity> findByAdminPetsTrue();
	List<Activity> findByWheelchairAcessibleTrue();
	List<Activity> findByLanguagesContaining(String language);
	
	long countByCategory(Category category);
	long countByCity(String city);

}
