package com.ecom.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long Id ;
    private Long productId ;
    private Long quantity ;
    private BigDecimal price ;
    private BigDecimal subTotal;


}
