package io.submanager.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateInviteRequest {

    @NotNull(message = "subscriptionId must not be null")
    private Integer subscriptionId;

    @NotBlank(message = "email must not be blank")
    private String email;
}
