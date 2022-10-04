package io.submanager.model;

import io.submanager.model.entity.Subscription;
import lombok.Data;

@Data
public class InviteResponse {

    public Integer id;
    public Subscription subscription;
    public String ownerEmail;
    public String email;
    public InviteStatus status;
}
