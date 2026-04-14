package com.example.orderservice.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.orderservice.Enums.OrderStatus;

public record OrderResponse(
        Long id,
        String userId,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemResponse> items,
        LocalDateTime createdAt
) {
    public record OrderItemResponse(
            String productId,
            Integer quantity,
            BigDecimal price
    ) {}
}
