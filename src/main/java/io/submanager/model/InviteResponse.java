package io.submanager.model;

import lombok.Data;

@Data
public class InviteResponse {

    public Integer id;
    public Integer subscriptionId;
    public String email;
    public InviteStatus status;
}
