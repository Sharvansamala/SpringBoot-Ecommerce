package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

import java.util.List;

public interface ProductService {
    public ProductResponse getAllProducts();


    ProductDTO addProduct(Long categoryId, Product product);
}
