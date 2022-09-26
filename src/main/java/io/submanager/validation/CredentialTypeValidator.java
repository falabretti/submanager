package io.submanager.validation;

import io.submanager.model.CredentialType;
import io.submanager.model.entity.Subscription;
import io.submanager.model.entity.SubscriptionCredentials;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CredentialTypeValidator implements ConstraintValidator<ValidCredentials, Subscription> {

    @Override
    public boolean isValid(Subscription subscription, ConstraintValidatorContext context) {

        CredentialType credentialType = subscription.getCredentialType();
        SubscriptionCredentials subscriptionCredentials = subscription.getSubscriptionCredentials();

        if (Objects.isNull(credentialType)) {
            return true;
        }

        if (credentialType.equals(CredentialType.INVITE) && !Objects.isNull(subscriptionCredentials)) {
            return false;
        }

        if (credentialType.equals(CredentialType.USER_AND_PASSWORD) && Objects.isNull(subscriptionCredentials)) {
            return false;
        }

        return true;
    }
}
