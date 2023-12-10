package com.cityway.activities.integration.repositories;

import java.time.LocalDate;
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
	
	List<ActivityDto> findByNameContaining(String name);
	List<ActivityDto> findByCategory(CategoryDto category);
	List<ActivityDto> findByCity(String city);
	List<ActivityDto> findByPriceBetween(double min, double max);
	List<ActivityDto> findByAdminPetsTrue();
	List<ActivityDto> findByWheelchairAccessibleTrue();
	List<ActivityDto> findByLanguagesContaining(String language);
	
	@Query("{'datesAvailables.?0': ?0}")
	List<ActivityDto> findByDate(String date);
	
	long countByCategory(CategoryDto category);
	long countByCity(String city);

}
