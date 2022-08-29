package io.submanager.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity(name = "sm_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    @NotBlank(message = "firstName must not be blank")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "lastName must not be blank")
    private String lastName;

    @JsonProperty(access = Access.READ_ONLY)
    @Column(name = "full_name", updatable = false, insertable = false)
    private String fullName;

    @Column(name = "email")
    @NotBlank(message = "email must not be blank")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "password must not be blank")
    private String password;
}
