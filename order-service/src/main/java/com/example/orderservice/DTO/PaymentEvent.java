package com.example.orderservice.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentEvent(
        String orderId,
        String userId,
        BigDecimal amount,
        String status,
        LocalDateTime timestamp) {
}
