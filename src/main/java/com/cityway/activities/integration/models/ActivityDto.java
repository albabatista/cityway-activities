package com.cityway.activities.integration.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@EqualsAndHashCode( of = "id")
@Document("activities")
public class ActivityDto{
	
	@Id
	private UUID id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private CategoryDto category;
	
	private String description;
	
	private double price;
	
	private String city;
	
	private String location;
	
	private List<String> languages;
	
	@jakarta.persistence.Temporal(TemporalType.DATE)
	private List<Date> datesAvailable;
	
	private boolean adminPets;
	
	private boolean wheelchairAcessible;

}
