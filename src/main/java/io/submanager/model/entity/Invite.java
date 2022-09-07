package io.submanager.model.entity;

import io.submanager.model.InviteStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "sm_invite")
public class Invite {

    @Id
    @Column(name = "invite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subscription_id")
    @NotNull(message = "subscriptionId must not be null")
    private Integer subscriptionId;

    @Column(name = "user_id")
    @NotNull(message = "userId must not be null")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "status must not be null")
    private InviteStatus status = InviteStatus.PENDING;
}
