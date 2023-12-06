package com.possystem.possystem.service;

import com.possystem.possystem.domain.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAll();
    List<Cart> saveAll(List<Cart> cartList);
    Cart save(Cart cart);
}
