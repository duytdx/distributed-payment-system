package com.example.commonlib.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderEvent(
        String orderId,
        String userId,
        String userEmail,
        BigDecimal amount,
        String status,
        LocalDateTime timestamp) {
}
