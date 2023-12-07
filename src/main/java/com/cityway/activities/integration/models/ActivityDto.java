package com.cityway.activities.integration.models;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cityway.activities.business.models.Availability;

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
	
	private String name;
	
	private CategoryDto category;
	
	private String description;
	
	private double price;
	
	private String city;
	
	private String location;
	
	private Set<String> languages;
	
	@Transient
	private Set<Availability> datesAvailables;
	
	private boolean adminPets;
	
	private boolean wheelchairAcessible;

}
