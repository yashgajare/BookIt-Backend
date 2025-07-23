package com.backend.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String resourceName;
	String field;
	String fieldName;
	long fieldId;

	public ResourceNotFoundException(String resourceName, String field, long fieldId) {
		super(String.format("%s not found with %s: %d", resourceName, field, fieldId));

		this.resourceName = resourceName;
		this.field = field;
		this.fieldId = fieldId;
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
