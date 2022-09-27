package io.submanager.repository;

import io.submanager.model.entity.PaymentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentNotificationRepository extends JpaRepository<PaymentNotification, Integer> {

}
