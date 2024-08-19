package com.cityway.activities.integration.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;
import com.cityway.activities.integration.repositories.CustomActivityRepository;


@Repository
public class CustomActivityRepositoryImpl implements CustomActivityRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<ActivityDto> findByParams(String name, CategoryDto category, Double minPrice, Double maxPrice,
			String city, String country, String language, String date, boolean adminPets,
			boolean wheelchairAccessible) {

		Query query = new Query();

		final String CASE_INSENSITIVE_PATTERN = "i";

		if (null != name && !name.isEmpty()) {
			query.addCriteria(Criteria.where("name").regex(name, CASE_INSENSITIVE_PATTERN));

		}if (null != country && !country.isEmpty()) {
			query.addCriteria(Criteria.where("country").is(country).regex(country, CASE_INSENSITIVE_PATTERN));

		}if (null != city && !city.isEmpty()) {
			query.addCriteria(Criteria.where("city").is(city).regex(city, CASE_INSENSITIVE_PATTERN));

		}if (null != language && !language.isEmpty()) {
			query.addCriteria(Criteria.where("languages").regex(language, CASE_INSENSITIVE_PATTERN));

		}if (null != category) {
			query.addCriteria(Criteria.where("category").is(category));

		}if (null != date) {
			query.addCriteria(Criteria.where("datesAvailables").elemMatch(Criteria.where(date).exists(true)));

		}if (null != minPrice && null != maxPrice) {
			query.addCriteria(Criteria.where("price").gte(minPrice).lt(maxPrice));

		}if (wheelchairAccessible) {
			query.addCriteria(Criteria.where("wheelchairAccessible").is(wheelchairAccessible));

		}if (adminPets) {
			query.addCriteria(Criteria.where("adminPets").is(adminPets));
		}

		return mongoTemplate.find(query.with(Sort.by(Sort.Direction.ASC, "name")), ActivityDto.class);
	}

}
