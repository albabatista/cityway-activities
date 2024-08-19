package com.cityway.activities.integration.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cityway.activities.integration.models.ActivityDto;

/**
 * The Interface ActivityRepository.
 */
@Repository
public interface ActivityRepository extends MongoRepository<ActivityDto, String>, CustomActivityRepository {

}
