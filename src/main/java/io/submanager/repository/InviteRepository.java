package io.submanager.repository;

import io.submanager.model.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Integer> {

    public List<Invite> findAllBySubscriptionId(Integer id);

    public List<Invite> findAllByUserId(Integer id);
}
