package io.submanager.repository;

import io.submanager.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value = "SELECT pay.* FROM sm_payment pay, sm_subscriber sub " +
            "WHERE pay.subscriber_id = sub.subscriber_id " +
            "AND sub.subscription_id = :subscriptionId " +
            "AND reference_period = :period", nativeQuery = true)
    public List<Payment> findAllBySubscriptionIdAndReferencePeriod(Integer subscriptionId, LocalDate period);
}
