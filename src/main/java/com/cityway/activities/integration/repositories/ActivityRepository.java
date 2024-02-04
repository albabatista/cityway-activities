package com.cityway.activities.integration.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;


/**
 * The Interface ActivityRepository.
 */
@Repository
public interface ActivityRepository extends MongoRepository<ActivityDto, String> {
	
	List<ActivityDto> findByNameContainingIgnoreCase(String name);
	List<ActivityDto> findByCategory(CategoryDto category);
	List<ActivityDto> findByCityIgnoreCase(String city);
	List<ActivityDto> findByPriceBetween(double min, double max);
	List<ActivityDto> findByAdminPetsTrue();
	List<ActivityDto> findByWheelchairAccessibleTrue();
	List<ActivityDto> findByLanguagesContainingIgnoreCase(String language);
	
	@Query("{'datesAvailables': {$elemMatch: {?0 :{$exists:true}}}}")
	List<ActivityDto> findByDate(String date);
	
}
