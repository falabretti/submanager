package io.submanager.controller;

import io.submanager.exception.ClientException;
import io.submanager.model.CredentialType;
import io.submanager.model.entity.Subscriber;
import io.submanager.model.entity.Subscription;
import io.submanager.model.entity.SubscriptionCredentials;
import io.submanager.model.entity.User;
import io.submanager.service.SubscriberService;
import io.submanager.service.SubscriptionService;
import io.submanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllOwnedSubscriptions(Principal principal) {
        User user = userService.getUser(principal);
        List<Subscription> subscriptions = subscriptionService.getAllByOwnerId(user.getId());
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getOwnedSubscription(Principal principal, @PathVariable Integer id) {
        User user = userService.getUser(principal);
        Optional<Subscription> subscription = subscriptionService.getByIdAndOwnerId(id, user.getId());
        return ResponseEntity.of(subscription);
    }

    @GetMapping("/subscribed")
    public ResponseEntity<List<Subscription>> getSubscribedSubscriptions(Principal principal) {

        User user = userService.getUser(principal);
        List<Subscriber> subscribers = subscriberService.getByUserId(user.getId());

        List<Subscription> subscriptions = subscribers.stream()
                .map(Subscriber::getSubscription)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(Principal principal,
                                                           @Valid @RequestBody Subscription subscription) {
        User user = userService.getUser(principal);
        subscription.setOwnerId(user.getId());
        Subscription createdSubscription = subscriptionService.create(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }

    @GetMapping("/credential/{id}")
    public ResponseEntity<SubscriptionCredentials> getCredentials(Principal principal, @PathVariable Integer id) {

        User user = userService.getUser(principal);
        Optional<Subscriber> subscriber = subscriberService.getByUserIdAndSubscriptionId(user.getId(), id);

        if (subscriber.isEmpty()) {
            throw new ClientException("Subscription does not exists");
        }

        Optional<Subscription> subscription = subscriptionService.get(id);

        if (subscription.get().getCredentialType().equals(CredentialType.INVITE)) {
            throw new ClientException("Subscription does not have credentials");
        }

        SubscriptionCredentials credentials = subscription.get().getSubscriptionCredentials();
        return ResponseEntity.ok(credentials);
    }
}
