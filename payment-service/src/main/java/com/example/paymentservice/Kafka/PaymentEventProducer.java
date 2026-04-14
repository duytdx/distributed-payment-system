package com.example.paymentservice.Kafka;

import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import com.example.paymentservice.DTO.PaymentEvent;

@Service
public class PaymentEventProducer {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentEvent(PaymentEvent event) {
        String topic = event.status().equals("SUCCESS") ? "payment.processed" : "payment.failed";
        kafkaTemplate.send(topic, event.orderId(), event);
    }
}
