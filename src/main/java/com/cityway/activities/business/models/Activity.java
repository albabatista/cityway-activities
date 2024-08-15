package com.cityway.activities.business.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class Activity extends RepresentationModel<Activity> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(example = "6571f425f55ae713957d8454")
	private String id;

	@NotBlank
	@Schema(example = "Cappadocia Hot Air Balloon Ride")
	private String name;

	@NotBlank
	@Schema(example = "ADVENTURE")
	private Category category;

	@Schema(example = "When you think of Cappadocia, the image that comes to mind is hundreds of colourful hot air balloons flying over spectacular natural landscapes. Witness the incredible sight for yourself!")
	private String description;

	@Schema(example = "180", defaultValue = "0")
	private double price;

	@NotBlank
	@Schema(example = "Cappadocia")
	private String city;

	@NotBlank
	@Schema(example = "Turkey")
	private String country;

	@Schema(example = "Sightseeing, Balloon Flight, Sunrise")
	private String location;

	@NotBlank
	@Schema(example = "[\"English\", \"Spanish\"]")
	private Set<String> languages;

	@Schema(description = "Url of the main image", example = "https://cityway.s3.eu-north-1.amazonaws.com/images/capadocia.png")
	private String image;

	@Schema(description = "Collection of urls of images uploaded in AWS S3", example = "[\"https://cityway.s3.eu-north-1.amazonaws.com/images/capadocia.png\"]")
	private Set<String> imagesGallery;

	@NotBlank
	@Schema(example = """
			[
			  {
			   "11/12/2023": [
			     "7:25",
			     "9:00"
			   ]
			 },
			 {
			   "18/12/2023": [
			     "7:45"
			   ]
			 }
			]""")
	private Set<Map<String, Set<String>>> datesAvailables;

	@Schema(example = "false", defaultValue = "false")
	private boolean adminPets;

	@Schema(example = "false", defaultValue = "false")
	private boolean wheelchairAccessible;

	public Activity() {
		this.imagesGallery = new HashSet<>();
	}

	@Schema (description = "HATEOAS self link",example = "{\"self\": {\"href\": \"http://localhost:8080/activities/6571f425f55ae713957d8454\"}}")
	@Override
	public Links getLinks() {
		return super.getLinks();
	}

}
