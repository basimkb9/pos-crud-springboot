package com.possystem.possystem.service.implementation;

import com.possystem.possystem.domain.Cart;
import com.possystem.possystem.domain.Variant;
import com.possystem.possystem.exception.OutOfStockException;
import com.possystem.possystem.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    public static List<Cart> cartItems = new ArrayList<>();

    @Autowired
    private VariantServiceImpl variantService;

    public List<Cart> getAll() {
        return cartItems;
    }

    public List<Cart> saveAll(List<Cart> cartList) {
        cartList.forEach(cart -> cartItems.add(cart));
        return cartItems;
    }

    public Cart save(Cart cart) {
        Boolean isAvailable = false;
        for (Cart cartItem : cartItems) {
            if(cartItem.getProductName().equals(cart.getProductName())
                    && cartItem.getVariantName().equals(cart.getVariantName())){
                isAvailable = variantService.checkAvailability(cartItem);
                if(isAvailable){
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItem.setSubTotal(cartItem.getSalePrice() * cartItem.getQuantity());
                    return cartItem;
                }
                else{
                    throw new OutOfStockException(String.format("Stock is Empty for " + cartItem.getProductName() +
                            " " + cartItem.getVariantName()));
                }
            }
        }


        cart.setQuantity(1L);
        isAvailable = variantService.checkAvailability(cart);
        if(isAvailable){
            Variant variant = variantService.getVariantByProductNameAndVariantName(cart.getVariantName(), cart.getProductName());
            cart.setSalePrice(variant.getSalePrice());
            cart.setCostPrice(variant.getCostPrice());
            cart.setSubTotal(cart.getSalePrice() * cart.getQuantity());

            cartItems.add(cart);
            return cart;
        }
        else{
            throw new OutOfStockException(String.format("Stock is Empty for " + cart.getProductName() +
                    " " + cart.getVariantName()));
        }
    }
}
