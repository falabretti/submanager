package io.submanager.model;

import lombok.Data;

@Data
public class ErrorResponse {

    private String status;
    private String error;
}
