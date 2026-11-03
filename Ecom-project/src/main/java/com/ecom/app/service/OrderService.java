package com.ecom.app.service;

import com.ecom.app.dto.OrderItemDTO;
import com.ecom.app.dto.OrderResponse;
import com.ecom.app.entity.*;
import com.ecom.app.repository.OrderRepository;
import com.ecom.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public Optional<OrderResponse> createOrder(String userId) {
        // Validate Cart items
        List<CartItem> cartItems = cartService.getCart(userId) ;
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        // Validate User
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId)) ;
        if(userOptional.isEmpty()){
            return Optional.empty();
        }
        User user = userOptional.get();
        // Cal total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // create order
        Order order =  new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream().map(item ->new OrderItem(
                null ,
                item.getProduct(),
                item.getQuantity(),
                item.getPrice(),
                order)).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // clean the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                            orderItem.getId(),
                            orderItem.getProduct().getId(),
                            orderItem.getQuantity().longValue(),
                            orderItem.getPrice(),
                            orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()

        );
    }
}
