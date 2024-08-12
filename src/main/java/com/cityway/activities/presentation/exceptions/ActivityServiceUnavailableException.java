package com.cityway.activities.presentation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ActivityBadRequestException.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ActivityServiceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActivityServiceUnavailableException(String msg) {
		super(msg);
	}

}
