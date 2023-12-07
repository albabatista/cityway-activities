package com.cityway.activities.business.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Class Availability.
 */
@Data
@NoArgsConstructor
public class Availability implements Serializable{
	
	private Map<LocalDate, List<LocalTime>> session;
	
	
}
