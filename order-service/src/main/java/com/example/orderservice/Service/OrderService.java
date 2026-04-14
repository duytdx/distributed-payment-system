package com.example.orderservice.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orderservice.DTO.CreateOrderRequest;
import com.example.orderservice.DTO.OrderResponse;
import jakarta.persistence.EntityNotFoundException;
import com.example.orderservice.Enums.OrderStatus;
import com.example.orderservice.Kafka.OrderEventProducer;
import com.example.orderservice.Model.Order;
import com.example.orderservice.Model.OrderItem;
import com.example.orderservice.Repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    @Transactional
    public OrderResponse createOrder(String userId, CreateOrderRequest request) {
        BigDecimal total = request.items().stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(total);
        List<OrderItem> orderItems = request.items().stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(item.productId());
                    orderItem.setQuantity(item.quantity());
                    orderItem.setPrice(item.price());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        orderEventProducer.publishOrderEvent(savedOrder);

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getStatus(),
                savedOrder.getTotal(),
                savedOrder.getItems().stream()
                        .map(item -> new OrderResponse.OrderItemResponse(
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice()))
                        .toList(),
                savedOrder.getCreatedAt());
    }

    @Transactional
    public void updateOrderStatus(String orderId, String paymentStatus) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        OrderStatus newStatus = "SUCCESS".equals(paymentStatus) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        order.setStatus(newStatus);
    }
}
