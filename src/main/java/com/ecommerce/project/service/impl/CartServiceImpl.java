package com.ecommerce.project.service.impl;

import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.respository.CartItemRepository;
import com.ecommerce.project.respository.CartRepository;
import com.ecommerce.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        //create a cart or find existing cart

        return null;
    }
}
