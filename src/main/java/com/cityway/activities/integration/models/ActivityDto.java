package com.cityway.activities.integration.models;

import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.convert.ValueConverter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cityway.activities.integration.annotations.impl.ToCapitalizeFullyConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@EqualsAndHashCode( of = "id")
@Document("activities")
public class ActivityDto{
	
	@Id
	private String id;
	
	@ValueConverter(ToCapitalizeFullyConverter.class)
    @Indexed(unique = true)
	private String name;
	
	private CategoryDto category;
	
	private String description;
	
	private double price;
	
	@ValueConverter(ToCapitalizeFullyConverter.class)
	private String city;
	
	@ValueConverter(ToCapitalizeFullyConverter.class)
	private String location;
	
	private Set<String> languages;
	
	private Set<Map<String, Set<String>>> datesAvailables;
	
	private boolean adminPets;
	
	private boolean wheelchairAccessible;

}
