package io.submanager.converter;

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

        InviteResponse inviteResponse = new InviteResponse();

        inviteResponse.setId(invite.getId());
        inviteResponse.setSubscriptionId(invite.getSubscriptionId());
        inviteResponse.setEmail(invitee.getEmail());
        inviteResponse.setStatus(invite.getStatus());

        // TODO add subscription name
        return inviteResponse;
    }

    public Invite fromSubscriptionAndInvitee(Subscription subscription, User invitee) {

        Invite invite = new Invite();
        invite.setSubscriptionId(subscription.getId());
        invite.setUserId(invitee.getId());

        return invite;
    }
}
