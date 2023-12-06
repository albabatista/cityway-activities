package com.cityway.activities.integration.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;


/**
 * The Interface ActivityRepository.
 */
@Repository
public interface ActivityRepository extends MongoRepository<ActivityDto, UUID> {
	
	List<ActivityDto> findByNameContaining(String name);
	List<ActivityDto> findByCategory(CategoryDto category);
	List<ActivityDto> findByCity(String city);
	List<ActivityDto> findByPriceBetween(double min, double max);
	List<ActivityDto> findByAdminPetsTrue();
	List<ActivityDto> findByWheelchairAcessibleTrue();
	List<ActivityDto> findByLanguagesContaining(String language);
	
	@Query("{'availability.date': ?0}")
	List<ActivityDto> findByDateAvailable(LocalDate date);
	
	long countByCategory(CategoryDto category);
	long countByCity(String city);

}
