package com.yogesh.ecom.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogesh.ecom.exceptions.ResourceNotFoundException;
import com.yogesh.ecom.model.Category;
import com.yogesh.ecom.model.Product;
import com.yogesh.ecom.payload.ProductDTO;
import com.yogesh.ecom.payload.ProductResponse;
import com.yogesh.ecom.repository.CategoryRepository;
import com.yogesh.ecom.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        // Implementation here
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
        product.setSpecialPrice(specialPrice);
        product.setImage("default.png");
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        return new ProductResponse(productDTOs);
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Product> products = productRepository.findByCategory(category);
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        return new ProductResponse(productDTOs);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword);
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        return new ProductResponse(productDTOs);
    }

    @Override
    public ProductDTO updateProduct(Product product, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
        existingProduct.setSpecialPrice(specialPrice);
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
    
        @Override
        public ProductDTO deleteProduct(Long productId) {
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
            productRepository.delete(existingProduct);
            return modelMapper.map(existingProduct, ProductDTO.class);
        }
}