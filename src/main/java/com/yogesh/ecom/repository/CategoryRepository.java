package com.yogesh.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.ecom.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
    
}
