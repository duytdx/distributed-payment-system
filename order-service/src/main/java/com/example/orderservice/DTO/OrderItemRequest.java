package com.example.orderservice.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotBlank(message = "Product ID is required") String productId,
        @Min(1) Integer quantity,
        @NotNull BigDecimal price) {

}
