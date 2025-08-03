package com.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.ApiResponse;
import com.backend.dtos.AvailabilityScheduleDto;
import com.backend.dtos.BusinessAddressDto;
import com.backend.dtos.BusinessCategoryDto;
import com.backend.dtos.BusinessInfoDto;
import com.backend.dtos.PortfolioImageDto;
import com.backend.dtos.StandardResponse;
import com.backend.exceptions.ResourceNotFoundException;
import com.backend.services.ProviderRegistrationService;

@RestController
@RequestMapping("/business-registration")
public class ProviderRegistrationController  {

	@Autowired
	private ProviderRegistrationService registrationService;
	
	@PostMapping("/step1")
	public ResponseEntity<?> saveBusinessInfo(@RequestBody BusinessInfoDto businessInfoDto) {
	    try {
	        if (businessInfoDto.getEmail() == null && businessInfoDto.getMobile_number() == null) {
	            ApiResponse<Object> errorResponse = ApiResponse.builder()
	                    .message("Either mobile number or email must be provided")
	                    .status(HttpStatus.BAD_REQUEST)
	                    .error("Validation failed: Missing contact information")
	                    .build();

	            return ResponseEntity.badRequest().body(new StandardResponse<>(errorResponse));
	        }

	        Long draftId = registrationService.saveBusinessInfo(businessInfoDto);

	        ApiResponse<Object> successResponse = ApiResponse.builder()
	                .message("Business info saved successfully")
	                .status(HttpStatus.OK)
	                .error(null)
	                .data(draftId)
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(successResponse));

	    } catch (Exception e) {
	        e.printStackTrace();
	        ApiResponse<Object> errorResponse = ApiResponse.builder()
	                .message("Failed to save business info")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(errorResponse));
	    }
	}

	
	@PostMapping("/step2/{draftId}")
	public ResponseEntity<?> saveBusinessCategory(@RequestBody BusinessCategoryDto categoryDto,  @PathVariable long draftId) {
	    try {
	        String result = registrationService.saveBusinessCategory(categoryDto);

	        ApiResponse<Object> successResponse = ApiResponse.builder()
	                .message("Business category saved successfully")
	                .status(HttpStatus.OK)
	                .error(null)
	                .data(result)
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(successResponse));

	    } catch (Exception e) {
	        ApiResponse<Object> errorResponse = ApiResponse.builder()
	                .message("Failed to save business category")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(errorResponse));
	    }
	}

	
	@PostMapping("/step3/{draftId}")
	public ResponseEntity<?> saveBusinessAddress(@RequestBody BusinessAddressDto addressDto, @PathVariable long draftId) {
	    try {
	        String result = registrationService.saveBusinessAddress(addressDto);

	        ApiResponse<Object> successResponse = ApiResponse.builder()
	                .message("Business address saved successfully")
	                .status(HttpStatus.OK)
	                .error(null)
	                .data(result)
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(successResponse));

	    } catch (Exception e) {
	        ApiResponse<Object> errorResponse = ApiResponse.builder()
	                .message("Failed to save business address")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(errorResponse));
	    }
	}

	@PostMapping("/step4/{draftId}")
	public ResponseEntity<ApiResponse<String>> saveBusinessAvailabilitySchedule(
	        @PathVariable Long draftId,
	        @RequestBody AvailabilityScheduleDto dto) {

	    ApiResponse<String> response;
	    try {
	        dto.setProviderId(draftId); // ensure correct draftId
	        String result = registrationService.saveBusinessAvailabiltySchedule(dto);
	        response = new ApiResponse<>("Availability saved successfully", HttpStatus.OK, null, result);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response = new ApiResponse<>("Failed to save availability", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@PostMapping("/step5/{draftId}")
	public ResponseEntity<ApiResponse<String>> savePortfolioImages(
	        @PathVariable Long draftId,
	        @ModelAttribute PortfolioImageDto imageDto) {

	    ApiResponse<String> response;
	    try {
	        imageDto.setProviderId(draftId);
	        String result = registrationService.saveBusinessPortfolioImage(imageDto);
	        response = new ApiResponse<>("Images uploaded successfully", HttpStatus.OK, null, result);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response = new ApiResponse<>("Failed to upload images", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	
	@PostMapping("/finalize/{draftId}")
	public ResponseEntity<ApiResponse<String>> finalizeRegistration(@PathVariable Long draftId) {

	    ApiResponse<String> response;
	    try {
	        String result = registrationService.saveBusinessDetails(draftId);
	        response = new ApiResponse<>("Registration finalized", HttpStatus.OK, null, result);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response = new ApiResponse<>("Failed to finalize registration", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	
}
