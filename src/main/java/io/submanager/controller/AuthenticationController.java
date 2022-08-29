package io.submanager.controller;

import io.submanager.model.entity.LogInRequest;
import io.submanager.model.entity.LogInResponse;
import io.submanager.service.CustomUserDetailsService;
import io.submanager.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/login")
    public ResponseEntity<LogInResponse> logIn(@RequestBody LogInRequest logInRequest) {
        authenticate(logInRequest.getEmail(), logInRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(logInRequest.getEmail());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new LogInResponse(token));
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
