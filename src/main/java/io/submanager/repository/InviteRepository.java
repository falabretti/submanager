package io.submanager.repository;

import io.submanager.model.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {

    @Query(value = "SELECT inv.* FROM sm_invite inv, sm_subscription sub WHERE inv.subscription_id = sub.subscription_id AND sub.owner_id = :id", nativeQuery = true)
    public List<Invite> findAllByOwnerId(Integer id);

    public List<Invite> findAllByUserId(Integer id);

    public Optional<Invite> findByIdAndUserId(Integer id, Integer userId);
}
