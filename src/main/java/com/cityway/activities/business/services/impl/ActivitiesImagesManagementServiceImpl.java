package com.cityway.activities.business.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cityway.activities.business.models.Activity;
import com.cityway.activities.business.services.ActivitiesImagesManagementService;
import com.cityway.activities.business.services.ActivityService;
import com.cityway.activities.presentation.exceptions.ActivityBadRequestException;
import com.cityway.activities.presentation.exceptions.ActivityNotFoundException;
import com.cityway.activities.presentation.exceptions.ActivityServiceUnavailableException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActivitiesImagesManagementServiceImpl implements ActivitiesImagesManagementService {
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	private AmazonS3 amazonS3Client;

	@Value("${cloud.aws.s3.bucket-name}")
	private String awsS3BucketName;

	@Value("${cloud.aws.s3.images-folder}")
	private String awsS3ImagesFolder;

	@Value("${cloud.aws.s3.bucket-endpoint}")
	private String awsS3BucketEndpoint;

	@Override
	public String uploadImage(String id, MultipartFile image) {
		Activity activity = activityService.read(id);
		
		if (null == activity)
			throw new ActivityNotFoundException(id);

		File imageToUpload = convertMultiPartToFile(image);
		uploadImageToS3(activity, imageToUpload);
		return activity.getImage();
	}
	
	

	@Override
	public void removeImage(String id) {
		Activity activity = activityService.read(id);
		
		if (null == activity)
			throw new ActivityNotFoundException(id);
		deleteImageFromS3(activity);	
	}


	@Override
	public void uploadImagesToGallery(String id, MultipartFile[] images) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeImagesFromGallery(String id) {
		// TODO Auto-generated method stub

	}
	
	
	// ***************************************************************
	//
	// PRIVATE METHODS
	//
	// ***************************************************************
	
	private File convertMultiPartToFile(MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());

		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
			return file;

		} catch (final IOException ex) {
			throw new ActivityBadRequestException(
					"Error converting the multi-part file to file, cause: {}" + ex.getMessage());

		}
	}
	

	private void uploadImageToS3(Activity activity, File image) {
		final String key = String.format("%s%s/%s",
				awsS3ImagesFolder, 
				activity.getCity().toLowerCase(), 
				image.getName().toLowerCase().trim());
		
		final PutObjectRequest putObjectRequest = new PutObjectRequest(awsS3BucketName, key, image);

		try {
			amazonS3Client.putObject(putObjectRequest);
			saveImage(key, activity);

		} catch (final AmazonServiceException ex) {
			throw new ActivityServiceUnavailableException(
					"Error while uploading file to AWS S3, cause: " + ex.getMessage());

		} finally {
			image.delete();
		}

	}

	private void saveImage(String key, Activity activity) {

		final String imageUrl = String.format("%s/%s", awsS3BucketEndpoint, key);

		activity.setImage(imageUrl);
		log.info("Image uploaded to AWS S3 sucessfully: {} and changed to the activity with id: {}", imageUrl,
				activity.getId());
		
		activityService.update(activity);
	}
	
	private void deleteImageFromS3(Activity activity) {
		final String key = String.format("%s%s%s",
				awsS3ImagesFolder, 
				activity.getCity().toLowerCase(), 
				activity.getImage().substring(activity.getImage().lastIndexOf("/")));
		final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(awsS3BucketName, key);
		
		try {
			amazonS3Client.deleteObject(deleteObjectRequest);
			activity.setImage(null);
			activityService.update(activity);
			
			log.info("Image deleted with key: {} from AWS S3 sucessfully and removed from the activity with id: {}",key,
					activity.getId());

		} catch (final AmazonServiceException ex) {
			throw new ActivityServiceUnavailableException(
					"Error while deleting file to AWS S3, cause: " + ex.getMessage());

		} 
	}

}
