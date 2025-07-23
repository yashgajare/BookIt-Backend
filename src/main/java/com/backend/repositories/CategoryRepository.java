package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
}
