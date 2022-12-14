package io.submanager.service;

import io.submanager.model.entity.Payment;
import io.submanager.model.entity.PaymentNotification;
import io.submanager.repository.PaymentNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentNotificationService extends AbstractService<PaymentNotification, Integer, PaymentNotificationRepository> {

    public List<PaymentNotification> getAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId);
    }

    public List<PaymentNotification> getAllByPayment(Payment payment) {
        return repository.findAllByPayment(payment);
    }

    public void removePaymentNotifications(Payment payment) {
        List<PaymentNotification> notifications = getAllByPayment(payment);
        repository.deleteAll(notifications);
    }
}
