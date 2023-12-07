package com.cityway.activities.business.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@EqualsAndHashCode( of = "id")
public class Activity implements Serializable{
	
	private String id;
	
	private String name;
	
	private Category category;
	
	private String description;
	
	private double price;
	
	private String city;
	
	private String location;
	
	private Set<String> languages;
	
	private Set<Map<String, List<String>>> datesAvailables;
	
	private boolean adminPets;
	
	private boolean wheelchairAcessible;

}
