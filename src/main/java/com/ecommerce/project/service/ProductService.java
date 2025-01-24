package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getProductsByCatgory(Long categoryId);

    ProductResponse getProductByKeyword(String keyword);
}
