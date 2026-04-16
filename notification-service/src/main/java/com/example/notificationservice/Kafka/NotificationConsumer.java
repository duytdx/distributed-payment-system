package com.example.notificationservice.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.notificationservice.DTO.NotificationEvent;
import com.example.notificationservice.Service.NotificationService;

@Service
public class NotificationConsumer {
    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "payment.processed", groupId = "notification-service")
    public void handlePaymentProcessed(NotificationEvent event) {
        System.out.println("Received payment.processed event: " + event);
        notificationService.handlePaymentSuccess(event);
    }

    @KafkaListener(topics = "payment.failed", groupId = "notification-service")
    public void handlePaymentFailed(NotificationEvent event) {
        System.out.println("Received payment.failed event: " + event);
        notificationService.handlePaymentFailure(event);
    }

}
