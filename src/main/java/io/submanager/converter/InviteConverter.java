package io.submanager.converter;

import io.submanager.exception.ClientException;
import io.submanager.model.CreateInviteRequest;
import io.submanager.model.InviteResponse;
import io.submanager.model.entity.Invite;
import io.submanager.model.entity.Subscription;
import io.submanager.model.entity.User;
import io.submanager.service.SubscriptionService;
import io.submanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InviteConverter {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    public InviteResponse fromInvite(Invite invite, User invitee) {

        Optional<Subscription> subscription = subscriptionService.get(invite.getSubscriptionId());
        if (subscription.isEmpty()) {
            throw new ClientException("Subscription does not exists");
        }

        Optional<User> user = userService.get(subscription.get().getOwnerId());
        if (subscription.isEmpty()) {
            throw new ClientException("User does not exists");
        }

        InviteResponse inviteResponse = new InviteResponse();

        inviteResponse.setId(invite.getId());
        inviteResponse.setSubscription(subscription.get());
        inviteResponse.setOwnerEmail(user.get().getEmail());
        inviteResponse.setEmail(invitee.getEmail());
        inviteResponse.setStatus(invite.getStatus());

        return inviteResponse;
    }

    public Invite fromSubscriptionAndInvitee(Subscription subscription, User invitee) {

        Invite invite = new Invite();
        invite.setSubscriptionId(subscription.getId());
        invite.setUserId(invitee.getId());

        return invite;
    }
}
