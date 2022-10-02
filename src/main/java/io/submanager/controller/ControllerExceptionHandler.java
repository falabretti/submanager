package io.submanager.controller;

import io.jsonwebtoken.JwtException;
import io.submanager.exception.ClientException;
import io.submanager.exception.ServiceException;
import io.submanager.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ClientException.class)
    private ResponseEntity<Object> handleClientException(ClientException exception, WebRequest request) {
        exception.printStackTrace();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = buildErrorResponse(exception.getMessage(), status);
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = ServiceException.class)
    private ResponseEntity<Object> handleServiceException(ServiceException exception, WebRequest request) {
        exception.printStackTrace();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = buildErrorResponse(exception.getMessage(), status);
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    private ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
        exception.printStackTrace();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponse response = buildErrorResponse("Invalid credentials", status);
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = JwtException.class)
    private ResponseEntity<Object> handleJwtException(JwtException exception, WebRequest request) {
        exception.printStackTrace();
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse response = buildErrorResponse(exception.getMessage(), status);
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {
        exception.printStackTrace();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = buildErrorResponse("An unknown error occurred", status);
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

    public ErrorResponse buildErrorResponse(String errorMessage, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(errorMessage);
        errorResponse.setStatus(status.name());
        return errorResponse;
    }
}
