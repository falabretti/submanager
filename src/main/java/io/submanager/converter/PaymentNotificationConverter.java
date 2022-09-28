package io.submanager.converter;

import io.submanager.model.PaymentNotificationResponse;
import io.submanager.model.PaymentResponse;
import io.submanager.model.entity.PaymentNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentNotificationConverter {
    
    @Autowired
    private PaymentConverter paymentConverter;

    public PaymentNotificationResponse fromPaymentNotification(PaymentNotification paymentNotification) {

        PaymentNotificationResponse paymentNotificationResponse = new PaymentNotificationResponse();

        PaymentResponse paymentResponse = paymentConverter.fromPayment(paymentNotification.getPayment());

        paymentNotificationResponse.setId(paymentNotification.getId());
        paymentNotificationResponse.setUserId(paymentNotification.getUserId());
        paymentNotificationResponse.setPayment(paymentResponse);
        paymentNotificationResponse.setNotificationType(paymentNotification.getNotificationType());

        return paymentNotificationResponse;
    }
}
