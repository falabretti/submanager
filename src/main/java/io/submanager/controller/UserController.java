package io.submanager.controller;

import io.submanager.model.entity.User;
import io.submanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> getLoggedInUser(Principal principal) {
        String email = principal.getName();
        Optional<User> user = userService.getByEmail(email);
        return ResponseEntity.of(user);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
       return ResponseEntity.ok(userService.getAll());
    }
}
