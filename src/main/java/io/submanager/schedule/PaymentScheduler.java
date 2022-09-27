package io.submanager.schedule;

import io.submanager.model.Periodicity;
import io.submanager.model.entity.Subscription;
import io.submanager.repository.specification.GenericSpecification;
import io.submanager.repository.specification.SearchCriteria;
import io.submanager.repository.specification.SearchOperation;
import io.submanager.service.PaymentService;
import io.submanager.service.SubscriptionService;
import io.submanager.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
public class PaymentScheduler {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Scheduled(cron = "0 0 1 1 * *")
    @Transactional
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public void createMonthlyPayments() {
        System.out.println("Running monthly payment schedule");

        LocalDate period = DateUtils.nowFromPeriodicity(Periodicity.MONTHLY);
        updatePayments(Periodicity.MONTHLY, period);
    }

    @Scheduled(cron = "0 0 2 1 1 *")
    @Transactional
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public void createYearlyPayments() {
        System.out.println("Running yearly payment schedule");

        LocalDate period = DateUtils.nowFromPeriodicity(Periodicity.YEARLY);
        updatePayments(Periodicity.YEARLY, period);
    }

    public void updatePayments(Periodicity periodicity, LocalDate period) {
        List<Subscription> periodSubscriptions = getSubscriptions(periodicity);
        periodSubscriptions.forEach(sub -> paymentService.updateSubscriptionPayments(sub, period));
    }

    private List<Subscription> getSubscriptions(Periodicity periodicity) {
        SearchCriteria searchCriteria = new SearchCriteria("periodicity", SearchOperation.EQUALS, periodicity);
        GenericSpecification<Subscription> specification = new GenericSpecification<>(searchCriteria);

        return subscriptionService.getAll(specification);
    }
}
