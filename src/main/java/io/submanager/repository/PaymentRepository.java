package io.submanager.repository;

import io.submanager.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, JpaSpecificationExecutor<Payment> {

    @Query(value = "SELECT pay.* FROM sm_payment pay, sm_subscriber sub " +
            "WHERE pay.subscriber_id = sub.subscriber_id " +
            "AND sub.subscription_id = :subscriptionId " +
            "AND reference_period = :period", nativeQuery = true)
    public List<Payment> findAllBySubscriptionIdAndReferencePeriod(Integer subscriptionId, LocalDate period);

    @Query(value = "SELECT pay.* FROM sm_payment pay, sm_subscriber subr, sm_subscription subpn " +
            "WHERE pay.subscriber_id = subr.subscriber_id " +
            "AND subr.subscription_id = subpn.subscription_id " +
            "AND pay.payment_id = :paymentId " +
            "AND subpn.owner_id = :ownerId", nativeQuery = true)
    public Optional<Payment> findByIdAndOwnerId(Integer paymentId, Integer ownerId);
}
