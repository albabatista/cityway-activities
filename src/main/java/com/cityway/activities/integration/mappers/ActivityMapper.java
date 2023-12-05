package com.cityway.activities.integration.mappers;

import org.mapstruct.Mapper;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.integration.models.ActivityDto;

/**
 * The Interface ActivityMapper.
 */
@Mapper (componentModel = "spring")
public interface ActivityMapper {
	
	ActivityDto activityToDto(Activity activity);
	
	Activity dtoToActivity(ActivityDto activityDto);

}
