package com.example.orderservice.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.commonlib.DTO.OrderEvent;
import com.example.orderservice.Model.Order;

@Service
public class OrderEventProducer {
    private static final String TOPIC = "order-events";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderEvent(Order order) {
        OrderEvent event = new OrderEvent(
                order.getId(),
                order.getUserId(),
                order.getTotal(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
        kafkaTemplate.send(TOPIC, order.getId(), event);
    }
}
