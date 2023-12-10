package com.cityway.activities.integration.models;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
	
    @Indexed(unique = true)
	private String name;
	
	private CategoryDto category;
	
	private String description;
	
	private double price;
	
	private String city;
	
	private String location;
	
	private Set<String> languages;
	
	private Set<Map<String, List<String>>> datesAvailables;
	
	private boolean adminPets;
	
	private boolean wheelchairAccessible;

}
