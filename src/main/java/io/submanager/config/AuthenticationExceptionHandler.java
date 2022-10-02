package io.submanager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.submanager.controller.ControllerExceptionHandler;
import io.submanager.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Autowired
    private ControllerExceptionHandler exceptionHandler;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException, ServletException {

        ErrorResponse errorResponse = exceptionHandler
                .buildErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
