package com.cityway.activities.business.models;

import java.io.Serializable;
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
	
	private String description;
	
	private double price;
	
	private String city;

}
