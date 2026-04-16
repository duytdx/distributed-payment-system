package com.example.notificationservice.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NotificationEvent(
        String orderId,
        String userId,
        String userEmail,
        BigDecimal amount,
        String status,
        LocalDateTime timestamp) {
}
