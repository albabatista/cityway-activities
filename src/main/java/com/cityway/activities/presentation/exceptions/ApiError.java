package com.cityway.activities.presentation.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@RequiredArgsConstructor 
@NoArgsConstructor
public class ApiError {
	
	@NonNull
	private HttpStatus state;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime date = LocalDateTime.now();
	
	@NonNull
	private String message;

}
