package com.example.paymentservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.paymentservice.Model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
