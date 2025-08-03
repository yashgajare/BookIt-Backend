package com.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.ApiResponse;
import com.backend.dtos.PopularCategoryDto;
import com.backend.dtos.SearchResultDto;
import com.backend.dtos.StandardResponse;
import com.backend.services.impl.HomeServiceImpl;

@RestController
public class HomeController {

	@Autowired
	private HomeServiceImpl serviceImpl;

	@GetMapping("/dashboard")
	public ResponseEntity<?> getPopularCategories(@RequestParam(defaultValue = "0") int page,
	                                              @RequestParam(defaultValue = "5") int size) {
	    try {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<PopularCategoryDto> categoryPage = serviceImpl.getPopularCategories(pageable);

	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("Popular categories fetched successfully")
	                .status(HttpStatus.OK)
	                .error(null)
	                .data(categoryPage)
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(apiResponse));

	    } catch (Exception e) {
	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("Failed to fetch popular categories")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(apiResponse));
	    }
	}


	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String query,
	                                @RequestParam(defaultValue = "0") int page,
	                                @RequestParam(defaultValue = "5") int size) {
	    if (query == null || query.trim().isEmpty()) {
	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("Search query must not be empty")
	                .status(HttpStatus.BAD_REQUEST)
	                .error("Invalid input")
	                .build();

	        return ResponseEntity.badRequest().body(new StandardResponse<>(apiResponse));
	    }

	    try {
	        Pageable pageable = PageRequest.of(page, size);
	        SearchResultDto result = serviceImpl.search(query, pageable);

	        String message = (result.getProviders().isEmpty() && result.getServices().isEmpty())
	                ? "No results found for: " + query
	                : "Search results fetched successfully";

	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message(message)
	                .status(HttpStatus.OK)
	                .error(null)
	                .data(result)
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(apiResponse));

	    } catch (Exception e) {
	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("An error occurred during search")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(apiResponse));
	    }
	}


}
