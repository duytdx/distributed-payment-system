package com.example.paymentservice.Service;

import org.springframework.stereotype.Service;
import com.example.paymentservice.Repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
}
