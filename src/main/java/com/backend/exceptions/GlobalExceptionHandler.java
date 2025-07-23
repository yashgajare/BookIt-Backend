package com.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.dtos.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
		String msg = ex.getMessage();
		ApiResponse response = ApiResponse.builder().message(msg).status(HttpStatus.NOT_FOUND).success(true).build(); 
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
}
