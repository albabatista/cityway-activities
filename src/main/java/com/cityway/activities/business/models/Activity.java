package com.cityway.activities.business.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Activity implements Serializable {

	@Hidden
	private String id;

	@Schema(description = "name of the activity (unique)", example = "Mont Saint Michel Day Trip")
	private String name;

	@Schema(description = "category of the activity", example = "DAY_TRIP")
	private Category category;

	@Schema(description = "description of the activity", example = "Set off on a full-day trip to Mont Saint Michel from Paris.")
	private String description;

	@Schema(description = "price of the activity", example = "159")
	private double price;

	@Schema(description = "city of the activity", example = "Paris")
	private String city;

	@Schema(description = "location of the activity", example = "Pullman Paris Tour Eiffel Hotel")
	private String location;

	@Schema(description = "languages availables for the activity", example = "[\"English\", \"Spanish\", \"Italian\", \"German\"]")
	private Set<String> languages;

	@Schema(description = "dates availables for the activity", example = "[\"11/12/2023:[\"7:25\", \"9:00\"]\"]")
	private Set<Map<String, List<String>>> datesAvailables;

	@Schema(description = "if admin pets in the activity", example = "false")
	private boolean adminPets;

	@Schema(description = "if the activity is accessible by wheelchair", example = "false")
	private boolean wheelchairAccessible;

}
