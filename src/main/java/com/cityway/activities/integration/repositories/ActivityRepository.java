package com.cityway.activities.integration.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;

/**
 * The Interface ActivityRepository.
 */
@Repository
public interface ActivityRepository extends MongoRepository<ActivityDto, UUID> {
	
	List<ActivityDto> findByCategory(Category category);
	List<ActivityDto> findByCity(String city);
	List<ActivityDto> findBetweenPriceRange(double min, double max);
	List<ActivityDto> findByAdminPetsTrue();
	List<ActivityDto> findByWheelchairAcessibleTrue();
	List<ActivityDto> findByLanguagesContaining(String language);
	
	long countByCategory(CategoryDto category);
	long countByCity(String city);

}
