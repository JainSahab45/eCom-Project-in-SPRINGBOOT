package com.ecom.app.dto;


import com.ecom.app.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id ;
    private BigDecimal totalAmount ;
    private OrderStatus orderStatus ;
    private List<OrderItemDTO> items ;

    private LocalDateTime createdAt ;


}
