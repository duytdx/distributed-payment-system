package com.example.orderservice.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.commonlib.DTO.OrderEvent;
import com.example.orderservice.Model.Order;

@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderEvent(Order order) {
        String orderId = String.valueOf(order.getId());
        OrderEvent event = new OrderEvent(
                orderId,
                order.getUserId(),
                order.getTotal(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
        kafkaTemplate.send("order.created", orderId, event);
    }
}
