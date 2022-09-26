package io.submanager.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "sm_subscriber")
public class Subscriber {

    @Id
    @Column(name = "subscriber_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", updatable = false)
    private Integer userId;

    @Column(name = "subscription_id", updatable = false)
    private Integer subscriptionId;
}
