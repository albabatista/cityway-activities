package com.cityway.activities.integration.models;

import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cityway.activities.integration.annotations.ToCapitalize;

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
	
	@ToCapitalize
    @Indexed(unique = true)
	private String name;
	
	private CategoryDto category;
	
	private String description;
	
	private double price;
	
	@ToCapitalize
	private String city;
	
	@ToCapitalize
	private String location;
	
	@ToCapitalize
	private Set<String> languages;
	
	private Set<Map<String, Set<String>>> datesAvailables;
	
	private boolean adminPets;
	
	private boolean wheelchairAccessible;

}
