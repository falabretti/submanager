package io.submanager.repository;

import io.submanager.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends
        JpaRepository<Subscription, Integer>,
        JpaSpecificationExecutor<Subscription> {

    public List<Subscription> findAllByOwnerId(Integer userId);

    public Optional<Subscription> findByIdAndOwnerId(Integer id, Integer userId);
}
