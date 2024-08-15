package com.cityway.activities.business.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.models.Category;
import com.cityway.activities.business.services.ActivityService;
import com.cityway.activities.integration.mappers.ActivityMapper;
import com.cityway.activities.integration.mappers.CategoryMapper;
import com.cityway.activities.integration.models.ActivityDto;
import com.cityway.activities.integration.models.CategoryDto;
import com.cityway.activities.integration.repositories.ActivityRepository;
import com.cityway.activities.presentation.exceptions.ActivityBadRequestException;
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
		if (null == activity) {
			throw new ActivityBadRequestException("Cannot save the activity because is null");
		}

		ActivityDto activityDto = capitalizeFields(activityMapper.activityToDto(activity));
		activityRepository.save(activityDto);

	}

	@Override
	public Activity read(String id) {
		Optional<ActivityDto> optional = activityRepository.findById(id);
		return optional.isPresent() ? activityMapper.dtoToActivity(optional.get()) : null;
	}

	@Override
	public void update(Activity activity) {
		if (null == activity) {
			throw new ActivityBadRequestException("Cannot save the activity because is null");

		} else if (!activityRepository.existsById(activity.getId())) {
			throw new ActivityNotFoundException(activity.getId());
		}

		ActivityDto activityDto = capitalizeFields(activityMapper.activityToDto(activity));
		activityRepository.save(activityDto);
	}

	@Override
	public void delete(String id) {
		if (!activityRepository.existsById(id)) {
			throw new ActivityNotFoundException(id);
		}
		activityRepository.deleteById(id);
	}

	@Override
	public void delete(Activity activity) {

		if (null == activity) {
			throw new ActivityBadRequestException("Cannot delete the activity because is null");

		} else if (!activityRepository.existsById(activity.getId())) {
			throw new ActivityNotFoundException(activity.getId());
		}

		ActivityDto activityDto = activityMapper.activityToDto(activity);
		activityRepository.delete(activityDto);
	}

	@Override
	public void updateImage(String id, String imageName) {
		if (null == imageName || imageName.isBlank()) {
			throw new ActivityBadRequestException("Cannot update the activity image because the url is invalid");

		} else if (!activityRepository.existsById(id)) {
			throw new ActivityNotFoundException(id);
		}

		Activity activity = read(id);
		activity.getImagesGallery().stream().filter(a -> a.contains(imageName)).findFirst()
				.ifPresent(activity::setImage);
		update(activity);
	}

	@Override
	public List<Activity> getAll() {
		List<ActivityDto> activitiesList = activityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByNameContaining(String name) {
		List<ActivityDto> activitiesList = activityRepository.findByNameContainingIgnoreCase(name);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByCategory(Category category) {
		CategoryDto categoryDto = categoryMapper.categoryToDto(category);
		List<ActivityDto> activitiesList = activityRepository.findByCategoryOrderByName(categoryDto);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByCity(String city) {
		List<ActivityDto> activitiesList = activityRepository.findByCityIgnoreCaseOrderByName(city);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByCountry(String country) {
		List<ActivityDto> activitiesList = activityRepository.findByCountryIgnoreCaseOrderByName(country);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByPriceBetween(double min, double max) {
		List<ActivityDto> activitiesList = activityRepository.findByPriceBetween(min, max);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByAdminPetsTrue() {
		List<ActivityDto> activitiesList = activityRepository.findByAdminPetsTrueOrderByName();
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByWheelchairAccessibleTrue() {
		List<ActivityDto> activitiesList = activityRepository.findByWheelchairAccessibleTrueOrderByName();
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByLanguaguesContaining(String languague) {
		List<ActivityDto> activitiesList = activityRepository.findByLanguagesContainingIgnoreCaseOrderByName(languague);
		return convertIntegrationToBusinessList(activitiesList);
	}

	@Override
	public List<Activity> getByDateAvailable(String date) {
		List<ActivityDto> activitiesList = activityRepository.findByDate(date);
		return convertIntegrationToBusinessList(activitiesList);
	}

	// ***************************************************************
	//
	// PRIVATE METHODS
	//
	// ***************************************************************

	private List<Activity> convertIntegrationToBusinessList(List<ActivityDto> activitiesDto) {
		return activitiesDto.stream().map(x -> activityMapper.dtoToActivity(x)).toList();
	}

	private ActivityDto capitalizeFields(ActivityDto activityDto) {
		activityDto.setName(WordUtils.capitalizeFully(activityDto.getName()).trim());
		activityDto.setCity(WordUtils.capitalizeFully(activityDto.getCity()).trim());
		activityDto.setCountry(WordUtils.capitalizeFully(activityDto.getCountry()).trim());
		activityDto.setLocation(WordUtils.capitalizeFully(activityDto.getLocation().trim()));

		Set<String> languages = Set
				.copyOf(activityDto.getLanguages().stream().map(WordUtils::capitalizeFully).map(String::trim).toList());
		activityDto.setLanguages(languages);

		return activityDto;
	}

}
