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
		super("Cannot find an activity with id: "+id);
	}
	
	public ActivityNotFoundException(Activity activity) {
		super("Cannot find the activity: "+activity);
	}
	
}
