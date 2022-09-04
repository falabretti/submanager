package io.submanager.controller;

import io.submanager.model.entity.Subscription;
import io.submanager.model.entity.User;
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

@RestController
@RequestMapping(path = "/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions(Principal principal) {
        User user = getUser(principal);
        List<Subscription> subscriptions = subscriptionService.getAllByOwnerId(user.getId());
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscription(Principal principal, @PathVariable Integer id) {
        User user = getUser(principal);
        Optional<Subscription> subscription = subscriptionService.getByIdAndOwnerId(id, user.getId());
        return ResponseEntity.of(subscription);
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(Principal principal,
                                                           @Valid @RequestBody Subscription subscription) {
        User user = getUser(principal);
        subscription.setOwnerId(user.getId());
        Subscription createdSubscription = subscriptionService.create(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }

    private User getUser(Principal principal) {
        String email = principal.getName();
        Optional<User> user = userService.getByEmail(email);
        return user.orElseThrow(() -> new RuntimeException("User does not exists"));
    }
}
