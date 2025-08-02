package com.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularCategoryDto {
    private Long categoryId;
    private String name;
    private String iconUrl;
    private Long providerCount;
}