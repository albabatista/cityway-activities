package com.cityway.activities.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.business.services.ActivityService;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.mappers.CategoryMapper;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;
import com.cityway.activities.integration.repositories.ActivityRepository;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private ActivityMapper activityMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public void create(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("Cannot save the activity because is null");
		}

		ActivityDto activityDto = activityMapper.activityToDto(activity);
		activityRepository.save(activityDto);
	}

	@Override
	public Activity read(String id) {
		Optional<ActivityDto> optional = activityRepository.findById(id);
		return optional.isPresent() ? activityMapper.dtoToActivity(optional.get()) : null;
	}

	@Override
	public void update(Activity activity) {
		create(activity);
	}

	@Override
	public void delete(String id) {
		activityRepository.deleteById(id);
	}

	@Override
	public void delete(Activity activity) {
		ActivityDto activityDto = activityMapper.activityToDto(activity);
		activityRepository.delete(activityDto);
	}

	@Override
	public List<Activity> getAll() {
		List<ActivityDto> activitiesList = activityRepository.findAll();
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByNameContaining(String name) {
		List<ActivityDto> activitiesList = activityRepository.findByNameContaining(name);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByCategory(Category category) {
		CategoryDto categoryDto = categoryMapper.categoryToDto(category);
		List<ActivityDto> activitiesList = activityRepository.findByCategory(categoryDto);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByCity(String city) {
		List<ActivityDto> activitiesList = activityRepository.findByCity(city);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByPriceBetween(double min, double max) {
		List<ActivityDto> activitiesList = activityRepository.findByPriceBetween(min, max);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByAdminPetsTrue() {
		List<ActivityDto> activitiesList = activityRepository.findByAdminPetsTrue();
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByWheelchairAccessibleTrue() {
		List<ActivityDto> activitiesList = activityRepository.findByWheelchairAccessibleTrue();
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByLanguaguesContaining(String languague) {
		List<ActivityDto> activitiesList = activityRepository.findByLanguagesContaining(languague);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByDateAvailable(String date) {
		List<ActivityDto> activitiesList = activityRepository.findByDate(date);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public long getTotalNumberOfActivities() {
		return activityRepository.count();
	}

	@Override
	public long getTotalNumberOfActivitiesByCategory(Category category) {
		CategoryDto categoryDto = categoryMapper.categoryToDto(category);
		return activityRepository.countByCategory(categoryDto);
	}

	@Override
	public long getTotalNumberOfActivitiesByCity(String city) {
		return activityRepository.countByCity(city);
	}

	private List<Activity> convertIntegrationToBusinessList(List<ActivityDto> activitiesDto) {
		return activitiesDto.stream().map(x -> activityMapper.dtoToActivity(x)).toList();
	}

}
