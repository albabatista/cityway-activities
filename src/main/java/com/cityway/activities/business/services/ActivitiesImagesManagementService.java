package com.cityway.activities.business.services;


import org.springframework.web.multipart.MultipartFile;

public interface ActivitiesImagesManagementService {

	String uploadImage(String id, MultipartFile image);
	void deleteImage(String id, String image);
}
