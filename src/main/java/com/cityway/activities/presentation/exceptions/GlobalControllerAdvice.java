package com.cityway.activities.presentation.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * The Class GlobalControllerAdvice.
 */
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ActivityNotFoundException.class)
	public ResponseEntity<ApiError> handleActivityNotFound(ActivityNotFoundException exception){
		
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {

		HttpStatus status = HttpStatus.valueOf(statusCode.value());
		ApiError apiError = new ApiError(status,ex.getMessage());
		
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
}
