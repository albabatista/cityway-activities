package com.cityway.activities.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {

	@Value("${cloud.aws.credentials.access-key}")
	private String awsS3AccessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String awsS3SecretKey;
	
	@Value("${cloud.aws.region.static}")
	private String awsS3Region;

	@Bean
	public AmazonS3 getAmazonS3Cient() {

		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);

		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(awsS3Region)
				.build();
	}

}
