package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Category;
import com.backend.entities.Subcategory;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	public Optional<Subcategory> findByNameAndCategory(String name, Category category);
	
}
