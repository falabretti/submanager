package io.submanager.model;

import io.submanager.model.entity.Payment;
import io.submanager.model.entity.Subscription;
import lombok.Data;

@Data
public class PaymentNotificationResponse {

    private Integer id;
    private Integer userId;
    private PaymentResponse payment;
    private PaymentNotificationType notificationType;
}
