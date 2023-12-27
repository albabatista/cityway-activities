package com.cityway.activities.business.models;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AdditionalPropertiesValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Hidden
	private String id;

	@NotBlank
	@Schema(example = "Mont Saint Michel Day Trip")
	private String name;

	@NotBlank
	@Schema(example = "DAY_TRIP")
	private Category category;

	@Schema(example = "Set off on a full-day trip to Mont Saint Michel from Paris.")
	private String description;

	@Schema(example = "159", defaultValue = "0")
	private double price;

	@NotBlank
	@Schema(example = "Paris")
	private String city;

	@NotBlank
	@Schema(example = "Pullman Paris Tour Eiffel Hotel")
	private String location;

	@NotBlank
	@Schema(example = "[\"English\", \"Spanish\", \"Italian\", \"German\"]")
	private Set<String> languages;

	@NotBlank
	@Schema(additionalProperties = AdditionalPropertiesValue.TRUE, 
		example = "{\"11/12/2023\":\"7:25\"}")
	private Set<Map<String, Set<String>>> datesAvailables;

	@Schema(example = "false", defaultValue = "false")
	private boolean adminPets;

	@Schema(example = "false", defaultValue = "false")
	private boolean wheelchairAccessible;

}
