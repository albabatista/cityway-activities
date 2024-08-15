package com.cityway.activities.business.services;

import org.springframework.web.multipart.MultipartFile;

public interface ActivitiesImagesManagementService {

	String uploadImage(String id, MultipartFile image);
	void removeImage(String id);
	
	void uploadImagesToGallery(String id, MultipartFile[] images);
	void removeImagesFromGallery(String id);
}
