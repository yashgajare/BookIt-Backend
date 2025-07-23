package com.backend.dtos;

import java.util.List;

import lombok.Data;

@Data
public class BusinessCategoryDto {

	private Long provider_id;
	private Long category_id;
	private List<String> subcategory;
}
