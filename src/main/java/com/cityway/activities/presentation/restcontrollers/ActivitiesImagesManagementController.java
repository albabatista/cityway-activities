package com.cityway.activities.presentation.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.services.ActivitiesImagesManagementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("/activities-images-management/")
@Tag(name = "Activities Images Management API")
public class ActivitiesImagesManagementController {

	@Autowired
	private ActivitiesImagesManagementService activitiesImagesManagementService;

	@Operation(summary = "Uploads a new main image to the activity", description = "Returns the url to the image")
	@ApiResponse(responseCode = "200", description = "Successfully uploaded", content = {
			@Content(schema = @Schema(implementation = Activity.class), mediaType = "application/json") })
	@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
			@Content(schema = @Schema()) })
	@PostMapping(path = "/{id}", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadImage(@PathVariable String id, @RequestPart("image") MultipartFile imageFile) {
		return ResponseEntity.ok(activitiesImagesManagementService.uploadImage(id, imageFile));
	}
	
	
	@Operation(summary = "Delete the image of the activity passed as parameter")
	@ApiResponse(responseCode = "204", description = "Successfully deleted", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "404", description = "Not found - Cannot find the activity", content = {
			@Content(schema = @Schema()) })
	@DeleteMapping("/{id}/{imageUrl}")
	public ResponseEntity<?> deleteImage(@PathVariable String id, @PathVariable String imageUrl) {
		activitiesImagesManagementService.deleteImage(id, imageUrl);
		return ResponseEntity.noContent().build();
	}
}
