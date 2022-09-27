package io.submanager.service;

import io.submanager.model.entity.PaymentNotification;
import io.submanager.repository.PaymentNotificationRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentNotificationService extends AbstractService<PaymentNotification, Integer, PaymentNotificationRepository> {

}
