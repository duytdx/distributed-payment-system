package com.example.paymentservice.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.commonlib.DTO.OrderEvent;
import com.example.paymentservice.Service.PaymentService;

@Service
public class PaymentEventConsumer {
    private final PaymentService paymentService;

    public PaymentEventConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order.created", groupId = "payment-service")
    public void handleOrderCreatedEvent(OrderEvent event) {
        paymentService.processPayment(event);
    }
}
