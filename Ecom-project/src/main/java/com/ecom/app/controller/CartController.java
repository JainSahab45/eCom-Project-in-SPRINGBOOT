package com.ecom.app.controller;

import com.ecom.app.dto.CartItemRequest;

import com.ecom.app.entity.CartItem;
import com.ecom.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                         @RequestBody CartItemRequest request){
        if(!cartService.addToCart(userId , request)){
            return ResponseEntity.badRequest().body("Product Out of Stock OR User Not Found OR Product Not Found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId ,@PathVariable Long productId){
            boolean deleted = cartService.deleteItemFromCart(userId , productId);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}
