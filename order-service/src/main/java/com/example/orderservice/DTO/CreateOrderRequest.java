package com.example.orderservice.DTO;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record CreateOrderRequest(
        @NotEmpty(message = "Order must have at least 1 item") @Valid List<OrderItemRequest> items) {
}
