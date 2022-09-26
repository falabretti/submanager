package io.submanager.service;

import io.submanager.model.InviteStatus;
import io.submanager.model.entity.Invite;
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

    public Invite acceptInvite(Invite invite) {
        invite.setStatus(InviteStatus.ACCEPTED);
        return update(invite);
    }

    public Invite rejectInvite(Invite invite) {
        invite.setStatus(InviteStatus.REJECTED);
        return update(invite);
    }
}
