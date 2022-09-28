package io.submanager.repository;

import io.submanager.model.entity.PaymentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentNotificationRepository extends JpaRepository<PaymentNotification, Integer> {

    List<PaymentNotification> findAllByUserId(Integer userId);
}
