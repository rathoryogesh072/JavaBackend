package com.yogesh.ecom.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.ecom.model.Category;
import com.yogesh.ecom.model.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);

}
