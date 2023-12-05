package com.cityway.activities.business.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.business.services.ActivityService;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.repositories.ActivityRepository;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private ActivityMapper activityMapper;

	@Override
	public Activity create(Activity activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity read(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Activity activity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Activity activity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Activity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getByCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getBetweenPriceRange(double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getByAdminPetsTrue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getByWheelchairAcessibleTrue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getByLanguaguesContaining(String languague) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalNumberOfActivities() {
		return activityRepository.count();
	}

	@Override
	public long getTotalNumberOfActivitiesByCategory(Category category) {
		return activityRepository.countByCategory(category);
	}

	@Override
	public long getTotalNumberOfActivitiesByCity(String city) {
		return activityRepository.countByCity(city);
	}

}
