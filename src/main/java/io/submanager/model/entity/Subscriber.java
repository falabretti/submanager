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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = " user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id")
    private Subscription subscription;
}
