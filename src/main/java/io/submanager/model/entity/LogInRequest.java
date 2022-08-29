package io.submanager.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogInRequest implements Serializable {

    private String email;
    private String password;
}
