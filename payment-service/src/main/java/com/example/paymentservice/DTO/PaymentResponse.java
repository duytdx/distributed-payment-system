package com.example.paymentservice.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.paymentservice.Enums.PaymentStatus;

public record PaymentResponse(
        Long id,
        String orderId,
        String userId,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime createdAt) {

}
