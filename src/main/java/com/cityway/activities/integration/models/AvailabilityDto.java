package com.cityway.activities.integration.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * The Class Availability.
 */
@Data
@Embeddable
public class AvailabilityDto {
	
	private LocalDate date;
	
	private List<LocalTime> sessions;
}
