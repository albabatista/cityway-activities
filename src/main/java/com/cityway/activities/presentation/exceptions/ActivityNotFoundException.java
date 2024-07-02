package com.cityway.activities.presentation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cityway.activities.business.models.Activity;

/**
 * The Class ActivityNotFoundException.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActivityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ActivityNotFoundException(String id) {
		super("Activity with id: "+id+" not found");
	}
	
	public ActivityNotFoundException(Activity activity) {
		super("Activity: "+activity+ "not found");
	}
	
}
