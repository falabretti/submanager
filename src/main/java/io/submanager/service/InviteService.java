package io.submanager.service;

import io.submanager.exception.ClientException;
import io.submanager.model.InviteStatus;
import io.submanager.model.entity.Invite;
import io.submanager.model.entity.Subscription;
import io.submanager.repository.InviteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InviteService extends AbstractService<Invite, Integer, InviteRepository> {

    public List<Invite> getAllByOwnerId(Integer userId) {
        return repository.findAllByOwnerId(userId);
    }

    public List<Invite> getAllByInviteeId(Integer userId) {
        return repository.findAllByUserId(userId);
    }

    public Optional<Invite> getByIdAndUserId(Integer id, Integer userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    public Invite create(Invite entity, Subscription subscription) {
        if (!hasSlotsAvailable(subscription)) {
            throw new ClientException("Subscription does not have any slots available");
        }
        return super.create(entity);
    }

    public Invite acceptInvite(Invite invite, Subscription subscription) {
        if (!hasSlotsAvailable(subscription)) {
            throw new ClientException("Subscription does not have any slots available");
        }
        invite.setStatus(InviteStatus.ACCEPTED);
        return update(invite);
    }

    public Invite rejectInvite(Invite invite) {
        invite.setStatus(InviteStatus.REJECTED);
        return update(invite);
    }

    private boolean hasSlotsAvailable(Subscription subscription) {
        int existentSubscribersCount = subscription.getSubscribers().size();
        Integer availableSlots = subscription.getSlots();
        return existentSubscribersCount < availableSlots;
    }
}
