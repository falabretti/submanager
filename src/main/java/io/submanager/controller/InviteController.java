package io.submanager.controller;

import io.submanager.converter.InviteConverter;
import io.submanager.exception.ClientException;
import io.submanager.model.CreateInviteRequest;
import io.submanager.model.InviteResponse;
import io.submanager.model.entity.Invite;
import io.submanager.model.entity.Subscription;
import io.submanager.model.entity.User;
import io.submanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/invite")
public class InviteController {

    @Autowired
    private InviteService inviteService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InviteConverter inviteConverter;

    @GetMapping("/sent")
    public ResponseEntity<List<Invite>> getUserSentInvites(Principal principal) {

        User user = userService.getUser(principal);

        List<Invite> invites = inviteService.getAllByOwnerId(user.getId());
        return ResponseEntity.ok(invites);
    }

    @GetMapping("/received")
    public ResponseEntity<List<Invite>> getUserReceivedInvites(Principal principal) {

        User user = userService.getUser(principal);

        List<Invite> invites = inviteService.getAllByInviteeId(user.getId());
        return ResponseEntity.ok(invites);
    }

    @PostMapping
    public ResponseEntity<InviteResponse> createInvite(Principal principal,
                                                       @Valid @RequestBody CreateInviteRequest inviteRequest) {
        User user = userService.getUser(principal);
        Optional<Subscription> subscription = subscriptionService
                .getByIdAndOwnerId(inviteRequest.getSubscriptionId(), user.getId());

        if (subscription.isEmpty()) {
            throw new ClientException("Subscription does not exists");
        }

        Optional<User> invitee = userService.getByEmail(inviteRequest.getEmail());

        if (invitee.isEmpty()) {
            throw new ClientException("Invitee with this email does not exists");
        }

        if (user.getId() == invitee.get().getId()) {
            throw new ClientException("Cannot create a invite for yourself");
        }

        Invite invite = inviteConverter.fromSubscriptionAndInvitee(subscription.get(), invitee.get());
        Invite createdInvite = inviteService.create(invite, subscription.get());
        InviteResponse inviteResponse = inviteConverter.fromInvite(createdInvite, invitee.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(inviteResponse);
    }

    @Transactional
    @PutMapping("/accept/{id}")
    public ResponseEntity<Invite> acceptInvite(Principal principal, @PathVariable Integer id) {

        User user = userService.getUser(principal);
        Optional<Invite> invite = inviteService.getByIdAndUserId(id, user.getId());

        if (invite.isEmpty()) {
            throw new ClientException("Invite does not exists");
        }

        Optional<Subscription> subscription = subscriptionService.get(invite.get().getSubscriptionId());

        if (subscription.isEmpty()) {
            throw new ClientException("Subscription does not exists");
        }

        Invite acceptedInvite = inviteService.acceptInvite(invite.get(), subscription.get());
        subscriberService.create(user, subscription.get());
        paymentService.updateSubscriptionPayments(subscription.get());

        return ResponseEntity.ok(acceptedInvite);
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Invite> rejectInvite(Principal principal, @PathVariable Integer id) {

        User user = userService.getUser(principal);
        Optional<Invite> invite = inviteService.getByIdAndUserId(id, user.getId());

        if (invite.isEmpty()) {
            throw new ClientException("Invite does not exists");
        }

        Invite rejectedInvite = inviteService.rejectInvite(invite.get());

        return ResponseEntity.ok(rejectedInvite);
    }
}
