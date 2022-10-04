package io.submanager.converter;

import io.submanager.model.PaymentResponse;
import io.submanager.model.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {

    public PaymentResponse fromPayment(Payment payment) {

        PaymentResponse paymentResponse = new PaymentResponse();

        paymentResponse.setId(payment.getId());
        paymentResponse.setSubscription(payment.getSubscriber().getSubscription());
        paymentResponse.setSubscriber(payment.getSubscriber());
        paymentResponse.setValue(payment.getValue());
        paymentResponse.setPeriodicity(payment.getPeriodicity());
        paymentResponse.setReferencePeriod(payment.getReferencePeriod());
        paymentResponse.setDueDate(payment.getDueDate());
        paymentResponse.setPaymentStatus(payment.getStatus());

        return paymentResponse;
    }
}
