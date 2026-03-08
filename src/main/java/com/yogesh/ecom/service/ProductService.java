package com.yogesh.ecom.service;

import com.yogesh.ecom.model.Product;
import com.yogesh.ecom.payload.ProductDTO;
import com.yogesh.ecom.payload.ProductResponse;

import jakarta.validation.Valid;

public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(Product product, Long productId);

    ProductDTO deleteProduct(Long productId);

}
