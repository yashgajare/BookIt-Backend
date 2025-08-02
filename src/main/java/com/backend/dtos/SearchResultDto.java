package com.backend.dtos;

import java.util.ArrayList;
import java.util.List;

import com.backend.entities.ServiceOffered;
import com.backend.entities.ServiceProvider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {

	private List<ServiceProvider> providers = new ArrayList<>();
	private List<ServiceOffered> services = new ArrayList<>();
}
