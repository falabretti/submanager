package io.submanager.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.submanager.model.PaymentStatus;
import io.submanager.model.Periodicity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "sm_payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscriber_id", referencedColumnName = "subscriber_id")
    private Subscriber subscriber;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicity")
    private Periodicity periodicity;

    @Column(name = "payment_value")
    private BigDecimal value;

    @Column(name = "reference_period")
    private LocalDate referencePeriod;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "payment")
    private List<PaymentNotification> paymentNotifications;
}
