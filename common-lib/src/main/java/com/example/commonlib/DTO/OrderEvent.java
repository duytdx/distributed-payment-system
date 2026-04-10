package com.example.commonlib.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderEvent {
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime timestamp;

    public OrderEvent() {}

    public OrderEvent(String orderId, String userId, BigDecimal amount, String status, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
