package com.yogesh.ecom.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.yogesh.ecom.model.Product;
import com.yogesh.ecom.payload.ProductDTO;
import com.yogesh.ecom.payload.ProductResponse;

import jakarta.validation.Valid;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

}
