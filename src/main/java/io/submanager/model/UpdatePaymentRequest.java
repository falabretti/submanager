package io.submanager.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePaymentRequest {

    @NotNull(message = "paymentId must not be null")
    private Integer paymentId;

    @NotNull(message = "status must not be null")
    private PaymentStatus status;
}
