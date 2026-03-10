package com.yogesh.ecom.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        @Autowired
        private FileService fileService;

        @Value("${project.image}")
        private String imageUploadPath;

        @Override
        public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
                // Implementation here
                Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
                Product product = modelMapper.map(productDTO, Product.class);
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
        public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
                Product existingProduct = productRepository.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
                Product product = modelMapper.map(productDTO, Product.class);
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

        @Override
        public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
                // Get the existing product
                Product existingProduct = productRepository.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
                // upload image to server
                String fileName = fileService.uploadImage(imageUploadPath, image);
                // update the product with new image name
                existingProduct.setImage(fileName);
                // save the updated product
                Product updatedProduct = productRepository.save(existingProduct);
                // return dto of updated product
                return modelMapper.map(updatedProduct, ProductDTO.class);

        }
}