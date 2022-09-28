package io.submanager.model;

import io.submanager.model.entity.Subscription;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentResponse {

    private Integer id;
    private Subscription subscription;
    private Periodicity periodicity;
    private BigDecimal value;
    private LocalDate referencePeriod;
    private LocalDate dueDate;
    private PaymentStatus paymentStatus;
}
