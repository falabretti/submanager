package io.submanager.service;

import io.submanager.model.entity.Subscriber;
import io.submanager.model.entity.Subscription;
import io.submanager.model.entity.User;
import io.submanager.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService extends AbstractService<Subscriber, Integer, SubscriberRepository> {

    public Subscriber create(User user, Subscription subscription) {
        Subscriber subscriber = new Subscriber();
        subscriber.setUser(user);
        subscriber.setSubscription(subscription);

        return create(subscriber);
    }

    public List<Subscriber> getByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    public Optional<Subscriber> getByUserIdAndSubscriptionId(Integer userId, Integer subscriptionId) {
        return repository.findByUserIdAndSubscriptionId(userId, subscriptionId);
    }
}
