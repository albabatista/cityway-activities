package com.cityway.activities.business.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.business.services.ActivityService;
import com.cityway.activities.business.utils.ActivitiesUtils;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.mappers.CategoryMapper;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;
import com.cityway.activities.integration.repositories.ActivityRepository;
import com.cityway.activities.presentation.exceptions.ActivityNotFoundException;

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
		save(activity);

	}

	@Override
	public Activity read(String id) {
		Optional<ActivityDto> optional = activityRepository.findById(id);
		return optional.isPresent() ? activityMapper.dtoToActivity(optional.get()) : null;
	}

	@Override
	public void update(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("Cannot save the activity because is null");

		} else if (!activityRepository.existsById(activity.getId())) {
			throw new ActivityNotFoundException("Activity with id " + activity.getId() + " not found");
		}
		
		save(activity);
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
		List<ActivityDto> activitiesList = activityRepository.findByNameContaining(ActivitiesUtils.capitalize(name));
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
		List<ActivityDto> activitiesList = activityRepository.findByCity(ActivitiesUtils.capitalize(city));
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
		List<ActivityDto> activitiesList = activityRepository.findByLanguagesContaining(ActivitiesUtils.capitalize(languague));
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
		return activityRepository.countByCity(ActivitiesUtils.capitalize(city));
	}

	// ***************************************************************
	//
	// PRIVATE METHODS
	//
	// ***************************************************************

	private List<Activity> convertIntegrationToBusinessList(List<ActivityDto> activitiesDto) {
		return activitiesDto.stream().map(x -> activityMapper.dtoToActivity(x)).toList();
	}

	private Set<String> capitalizeStringsFromList(Set<String> list) {
		List<String> resultList = list.stream().map(ActivitiesUtils::capitalize).toList();
		return Set.copyOf(resultList);
	}

	private void save(Activity activity) {
		ActivityDto activityDto = activityMapper.activityToDto(activity);

		UnaryOperator<ActivityDto> capitalizeFields = a -> {
			a.setName(ActivitiesUtils.capitalize(a.getName()));
			a.setCity(ActivitiesUtils.capitalize(a.getCity()));
			a.setLanguages(capitalizeStringsFromList(a.getLanguages()));
			return a;
		};

		activityDto = capitalizeFields.apply(activityDto);
		activityRepository.save(activityDto);
	}

}
