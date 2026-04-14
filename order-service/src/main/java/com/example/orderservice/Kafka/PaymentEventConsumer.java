package com.example.orderservice.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.orderservice.DTO.PaymentEvent;
import com.example.orderservice.Service.OrderService;

@Service
public class PaymentEventConsumer {
    private final OrderService orderService;

    public PaymentEventConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = {"payment.processed", "payment.failed"}, groupId = "order-service")
    public void handlePaymentEvent(PaymentEvent event) {
        orderService.updateOrderStatus(event.orderId(), event.status());
    }
}
