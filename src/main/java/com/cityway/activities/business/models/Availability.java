package com.cityway.activities.business.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

/**
 * The Class Availability.
 */
@Data
public class Availability {
	
	private LocalDate date;
	
	private List<LocalTime> sessions;
}
