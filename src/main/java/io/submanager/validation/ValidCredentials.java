package io.submanager.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CredentialTypeValidator.class })
public @interface ValidCredentials {

    String message() default "{io.submanager.validation.ValidCredentials.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
