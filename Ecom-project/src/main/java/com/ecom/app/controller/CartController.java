package com.ecom.app.controller;

import com.ecom.app.dto.CartItemRequest;

import com.ecom.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    @PostMapping
    public RequestEntity<Void> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request){
        CartService.addToCart(userId , request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
