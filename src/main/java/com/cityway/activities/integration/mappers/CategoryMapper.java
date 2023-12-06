package com.cityway.activities.integration.mappers;

import org.mapstruct.Mapper;

import com.cityway.activities.business.models.Category;
import com.cityway.activities.integration.models.CategoryDto;

/**
 * The Interface CategoryMapper.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryDto categoryToDto(Category category);

	Category dtoToCategory(CategoryDto categoryDto);

}
