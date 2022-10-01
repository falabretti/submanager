package io.submanager.model.entity;

import io.submanager.model.PaymentNotificationType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "sm_payment_notification")
public class PaymentNotification {

    @Id
    @Column(name = "payment_notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private PaymentNotificationType notificationType;
}
