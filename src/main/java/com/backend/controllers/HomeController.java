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

import com.backend.dtos.PopularCategoryDto;
import com.backend.dtos.SearchResultDto;
import com.backend.services.impl.HomeServiceImpl;

@RestController
public class HomeController {

	@Autowired
	private HomeServiceImpl serviceImpl;

	@GetMapping("/dashboard")
	public ResponseEntity<Page<PopularCategoryDto>> getPopularCategories(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(serviceImpl.getPopularCategories(pageable));
	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String query, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		if (query == null || query.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Search query must not be empty");
		}

		try {

			Pageable pageable = PageRequest.of(page, size);
			SearchResultDto result = serviceImpl.search(query, pageable);

			if (result.getProviders().isEmpty() && result.getServices().isEmpty()) {
				return ResponseEntity.ok("No results found for: " + query);
			}

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during search: " + e.getMessage());
		}
	}

}
