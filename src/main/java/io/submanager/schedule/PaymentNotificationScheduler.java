package io.submanager.schedule;

import io.submanager.model.PaymentNotificationType;
import io.submanager.model.PaymentStatus;
import io.submanager.model.entity.Payment;
import io.submanager.model.entity.PaymentNotification;
import io.submanager.repository.specification.GenericSpecification;
import io.submanager.repository.specification.SearchCriteria;
import io.submanager.repository.specification.SearchOperation;
import io.submanager.service.PaymentNotificationService;
import io.submanager.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentNotificationScheduler {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentNotificationService paymentNotificationService;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public void createPaymentNotifications() {
        System.out.println("Running notifications schedule");
        List<Payment> payments = getPendingPaymentsForNotifications();
        createNotifications(payments);
    }

    private List<Payment> getPendingPaymentsForNotifications() {
        LocalDate targetDate = LocalDate.now().plusDays(7);
        SearchCriteria dateCriteria = new SearchCriteria("dueDate", SearchOperation.LESS_OR_EQUAL, targetDate);
        SearchCriteria statusCriteria = new SearchCriteria("status", SearchOperation.EQUALS, PaymentStatus.PENDING);

        Specification<Payment> specification = Specification
                .where(new GenericSpecification(dateCriteria))
                .and(new GenericSpecification(statusCriteria));

        return paymentService.getAll(specification);
    }

    private void createNotifications(List<Payment> payments) {

        List<PaymentNotification> paymentNotifications = new ArrayList<>();

        for (Payment payment : payments) {
            PaymentNotification paymentNotification = new PaymentNotification();

            paymentNotification.setUserId(payment.getSubscriber().getUser().getId());
            paymentNotification.setPayment(payment);

            if (isPaymentOverdue(payment)) {
                paymentNotification.setNotificationType(PaymentNotificationType.PAYMENT_OVERDUE);
            } else {
                paymentNotification.setNotificationType(PaymentNotificationType.PAYMENT_PENDING);
            }

            if (paymentHasNotificationOfType(payment, paymentNotification)) {
                continue;
            }

            paymentNotifications.add(paymentNotification);
        }

        paymentNotificationService.createAll(paymentNotifications);
    }

    private boolean isPaymentOverdue(Payment payment) {
        return payment.getDueDate().isBefore(LocalDate.now());
    }

    private boolean paymentHasNotificationOfType(Payment payment, PaymentNotification paymentNotification) {
        return payment.getPaymentNotifications().stream()
                .map(PaymentNotification::getNotificationType)
                .anyMatch(paymentNotification.getNotificationType()::equals);
    }
}
