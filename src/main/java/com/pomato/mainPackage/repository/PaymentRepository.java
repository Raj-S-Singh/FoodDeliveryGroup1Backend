package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByPaymentId();
}
