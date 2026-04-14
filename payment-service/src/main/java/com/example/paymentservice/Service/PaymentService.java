package com.example.paymentservice.Service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.commonlib.DTO.OrderEvent;
import com.example.paymentservice.DTO.PaymentEvent;
import com.example.paymentservice.DTO.PaymentResponse;
import com.example.paymentservice.Repository.PaymentRepository;
import com.example.paymentservice.Enums.PaymentStatus;
import com.example.paymentservice.Model.Payment;
import com.example.paymentservice.Kafka.PaymentEventProducer;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final IdempotencyService idempotencyService;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentService(PaymentRepository paymentRepository, IdempotencyService idempotencyService,
            PaymentEventProducer paymentEventProducer) {
        this.paymentRepository = paymentRepository;
        this.idempotencyService = idempotencyService;
        this.paymentEventProducer = paymentEventProducer;
    }

    public void processPayment(OrderEvent orderEvent) {
        if (idempotencyService.isDuplicate(orderEvent.orderId())) {
            return;
        }

        if (paymentRepository.existsByOrderId(orderEvent.orderId())) {
            return;
        }

        Payment payment = new Payment();
        payment.setOrderId(orderEvent.orderId());
        payment.setUserId(orderEvent.userId());
        payment.setAmount(orderEvent.amount());
        payment.setStatus(PaymentStatus.PENDING);

        try {
            paymentRepository.save(payment);
        } catch (DataIntegrityViolationException e) {
            // Concurrent duplicate insert — another instance is already processing this order
            return;
        }

        boolean paymentSuccess = simulatePaymentProcessing();

        payment.setStatus(paymentSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        paymentRepository.save(payment);

        idempotencyService.markDone(orderEvent.orderId());
        paymentEventProducer.publishPaymentEvent(new PaymentEvent(
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus().name(),
                orderEvent.timestamp()));
    }

    public PaymentResponse getByOrderId(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for orderId: " + orderId));
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt());
    }

    private Boolean simulatePaymentProcessing() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return Math.random() < 0.8;
    }
}
