package io.submanager.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.submanager.model.Periodicity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "sm_subscription")
public class Subscription {

    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(access = Access.READ_ONLY)
    @Column(name = "owner_id", updatable = false)
    private Integer ownerId;

    @Column(name = "name")
    @NotBlank(message = "name must not be null")
    private String name;

    @Column(name = "slots")
    @NotNull(message = "slots must not be null")
    private Integer slots;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicity")
    @NotNull(message = "periodicity must not be null")
    private Periodicity periodicity;

    @Column(name = "due_date")
    @NotNull(message = "dueDate must no be null")
    private LocalDate dueDate;

    @Column(name = "value")
    @NotNull(message = "value must not be null")
    private BigDecimal value;
}

