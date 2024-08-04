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
	List<ActivityDto> findByCategoryOrderByName(CategoryDto category);
	List<ActivityDto> findByCityIgnoreCaseOrderByName(String city);
	List<ActivityDto> findByCountryIgnoreCaseOrderByName(String country);
	List<ActivityDto> findByPriceBetween(double min, double max);
	List<ActivityDto> findByAdminPetsTrueOrderByName();
	List<ActivityDto> findByWheelchairAccessibleTrueOrderByName();
	List<ActivityDto> findByLanguagesContainingIgnoreCaseOrderByName(String language);
	
	@Query("{'datesAvailables': {$elemMatch: {?0 :{$exists:true}}}}")
	List<ActivityDto> findByDate(String date);
	
}
