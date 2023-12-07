package com.cityway.activities.integration.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailabilityDto{
	
	private Map<LocalDate, List<LocalTime>> session;

}
