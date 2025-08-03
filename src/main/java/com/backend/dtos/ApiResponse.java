package com.backend.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
	
	private String message;
    private HttpStatus status;
    private String error;
    private T data;
	/**
	 * @param message
	 * @param status
	 * @param error
	 */
	public ApiResponse(String message, HttpStatus status, String error) {
		this.message = message;
		this.status = status;
		this.error = error;
	}

}
