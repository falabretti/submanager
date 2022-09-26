package io.submanager.service;

import io.submanager.model.entity.Invite;
import io.submanager.repository.InviteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService extends AbstractService<Invite, Integer, InviteRepository> {

    public List<Invite> getAllByOwnerId(Integer userId) {
        return repository.findAllByOwnerId(userId);
    }

    public List<Invite> getAllByInviteeId(Integer userId) {
        return repository.findAllByUserId(userId);
    }
}
