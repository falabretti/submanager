package io.submanager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LogInResponse implements Serializable {

    public String jwtToken;
}
