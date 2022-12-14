package io.submanager.repository;

import io.submanager.model.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

    public List<Subscriber> findByUserId(Integer userId);
    
    public Optional<Subscriber> findByUserIdAndSubscriptionId(Integer userId, Integer subscriptionId);
}
