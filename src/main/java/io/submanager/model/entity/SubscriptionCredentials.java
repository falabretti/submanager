package io.submanager.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.submanager.converter.CredentialConverter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity(name = "sm_subscription_credential")
public class SubscriptionCredentials {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "subscription_credential_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "subscriptionCredentials")
    private Subscription subscription;

    @Column(name = "credential_user")
    @NotBlank(message = "credential user must not be blank")
    @Convert(converter = CredentialConverter.class)
    private String user;

    @Column(name = "credential_password")
    @NotBlank(message = "credential password must not be blank")
    @Convert(converter = CredentialConverter.class)
    private String password;
}
