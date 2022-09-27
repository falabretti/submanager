package io.submanager.service;

import io.submanager.model.entity.Subscription;
import io.submanager.repository.SubscriptionRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService extends AbstractService<Subscription, Integer, SubscriptionRepository> {

    public List<Subscription> getAllByOwnerId(Integer userId) {
        return repository.findAllByOwnerId(userId);
    }

    public Optional<Subscription> getByIdAndOwnerId(Integer id, Integer userId) {
        return repository.findByIdAndOwnerId(id, userId);
    }

    public List<Subscription> getAll(Specification<Subscription> specification) {
        return repository.findAll(specification);
    }
}
