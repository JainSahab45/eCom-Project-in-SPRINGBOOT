package com.ecom.app.repository;

import com.ecom.app.entity.CartItem;
import com.ecom.app.entity.Product;
import com.ecom.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);
}
