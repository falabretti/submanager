package io.submanager.service;

import io.submanager.model.PaymentStatus;
import io.submanager.model.entity.Payment;
import io.submanager.model.entity.Subscriber;
import io.submanager.model.entity.Subscription;
import io.submanager.repository.PaymentRepository;
import io.submanager.util.DateUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService extends AbstractService<Payment, Integer, PaymentRepository> {

    public void updateSubscriptionPayments(Subscription subscription) {
        LocalDate period = DateUtils.nowFromPeriodicity(subscription.getPeriodicity());
        updateSubscriptionPayments(subscription, period);
    }

    @Transactional
    public void updateSubscriptionPayments(Subscription subscription, LocalDate period) {

        List<Subscriber> subscribers = subscription.getSubscribers();
        BigDecimal subscriptionValue = subscription.getValue();
        int subscriberCount = subscribers.size();

        if (subscriberCount == 0) {
            System.out.println("No subscribers to charge for");
            return;
        }

        BigDecimal individualValue = subscriptionValue
                .divide(BigDecimal.valueOf(subscriberCount + 1), 2, RoundingMode.HALF_EVEN); // +1 owner

        List<Payment> periodPayments = getAllBySubscriptionIdAndReferencePeriod(subscription.getId(), period);
        boolean hasPaidPayment = periodPayments.stream()
                .map(Payment::getStatus)
                .anyMatch(PaymentStatus.PAID::equals);

        if (periodPayments.size() == 0) {
            subscribers.forEach(sub -> createPayment(sub, individualValue, period));
        } else if (!hasPaidPayment) {
            List<Integer> subscribersWithPaymentIds = periodPayments.stream()
                    .map(Payment::getSubscriber)
                    .map(Subscriber::getId)
                    .collect(Collectors.toList());

            List<Subscriber> subscribersWithoutPayment = subscribers.stream()
                    .filter(sub -> !subscribersWithPaymentIds.contains(sub.getId()))
                    .collect(Collectors.toList());

            periodPayments.forEach(pay -> pay.setValue(individualValue));
            periodPayments.forEach(pay -> update(pay));
            subscribersWithoutPayment.forEach(sub -> createPayment(sub, individualValue, period));
        } else {
            System.out.println("A payment was already made for this period");
        }
    }

    private Payment createPayment(Subscriber subscriber, BigDecimal value, LocalDate period) {

        Subscription subscription = subscriber.getSubscription();

        Payment payment = new Payment();
        payment.setSubscriber(subscriber);
        payment.setPeriodicity(subscription.getPeriodicity());
        payment.setValue(value);
        payment.setReferencePeriod(period);
        payment.setDueDate(period.withDayOfMonth(subscription.getDueDate().getDayOfMonth()));
        payment.setStatus(PaymentStatus.PENDING);

        return create(payment);
    }

    public List<Payment> getAllBySubscriptionIdAndReferencePeriod(Integer subscriptionId, LocalDate period) {
        return repository.findAllBySubscriptionIdAndReferencePeriod(subscriptionId, period);
    }

    public Optional<Payment> getByIdAndOwnerId(Integer paymentId, Integer ownerId) {
        return repository.findByIdAndOwnerId(paymentId, ownerId);
    }

    public List<Payment> getAll(Specification specification) {
        return repository.findAll(specification);
    }

    public List<Payment> getAllDueBySubscriberUserId(Integer userId) {
        return repository.findAllDueBySubscriberUserId(userId);
    }

    public List<Payment> getAllDueByOwnerId(Integer userId) {
        return repository.findAllDueByOwnerUserId(userId);
    }
}
