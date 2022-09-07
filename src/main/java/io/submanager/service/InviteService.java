package io.submanager.service;

import io.submanager.model.entity.Invite;
import io.submanager.repository.InviteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService extends AbstractService<Invite, Integer, InviteRepository> {

    public List<Invite> getAllBySubscriptionId(Integer subscriptionId) {
        return repository.findAllBySubscriptionId(subscriptionId);
    }

    public List<Invite> getAllByInviteeId(Integer userId) {
        return repository.findAllByUserId(userId);
    }
}
