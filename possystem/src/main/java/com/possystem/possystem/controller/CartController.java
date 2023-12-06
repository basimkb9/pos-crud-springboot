package com.possystem.possystem.controller;

import com.possystem.possystem.domain.Cart;
import com.possystem.possystem.service.implementation.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CartController {

    @Autowired
    CartServiceImpl cartService;

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getAll(){
        return ResponseEntity.ok(cartService.getAll());
    }

    @PutMapping("/cart")
    public ResponseEntity<List<Cart>> putAll(@RequestBody List<Cart> cartItems){
        return ResponseEntity.ok(cartService.saveAll(cartItems));
    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> putItem(@RequestBody Cart cart){
        return ResponseEntity.ok(cartService.save(cart));
    }

}
