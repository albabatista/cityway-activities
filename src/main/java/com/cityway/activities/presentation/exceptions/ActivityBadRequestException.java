package com.cityway.activities.presentation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ActivityBadRequestException.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActivityBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActivityBadRequestException(String msg) {
		super(msg);
	}


}
