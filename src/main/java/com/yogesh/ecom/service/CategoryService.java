package com.yogesh.ecom.service;


import com.yogesh.ecom.payload.CategoryDTO;
import com.yogesh.ecom.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}
