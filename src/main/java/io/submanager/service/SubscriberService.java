package io.submanager.service;

import io.submanager.model.entity.Subscriber;
import io.submanager.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService extends AbstractService<Subscriber, Integer, SubscriberRepository> {

    public Subscriber create(Integer userId, Integer subscriptionId) {
        Subscriber subscriber = new Subscriber();
        subscriber.setUserId(userId);
        subscriber.setSubscriptionId(subscriptionId);

        return create(subscriber);
    }
}
