package com.yogesh.ecom.service;

import java.util.List;

import com.yogesh.ecom.model.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    void updateCategory(Long id, Category category);
}
