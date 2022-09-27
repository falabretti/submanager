package io.submanager.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PaymentSchedulerTestRequest {

    @NotNull(message = "periodicity must not be null")
    private Periodicity periodicity;

    @NotNull(message = "period must not be null")
    private LocalDate period;
}
