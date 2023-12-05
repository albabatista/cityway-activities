package com.cityway.activities.business.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@EqualsAndHashCode( of = "id")
public class Activity implements Serializable{
	
	private UUID id;
	
	private String name;
	
	private Category category;
	
	private String description;
	
	private double price;
	
	private String city;
	
	private String location;
	
	private List<String> languages;
	
	private List<Date> datesAvailable;
	
	private boolean adminPets;
	
	private boolean wheelchairAcessible;

}
