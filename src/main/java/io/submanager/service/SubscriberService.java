package io.submanager.service;

import io.submanager.model.entity.Subscriber;
import io.submanager.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriberService extends AbstractService<Subscriber, Integer, SubscriberRepository> {

    public Subscriber create(Integer userId, Integer subscriptionId) {
        Subscriber subscriber = new Subscriber();
        subscriber.setUserId(userId);
        subscriber.setSubscriptionId(subscriptionId);

        return create(subscriber);
    }

    public Optional<Subscriber> getByUserIdAndSubscriptionId(Integer userId, Integer subscriptionId) {
        return repository.findByUserIdAndSubscriptionId(userId, subscriptionId);
    }
}
