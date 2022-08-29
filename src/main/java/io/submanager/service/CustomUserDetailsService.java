package io.submanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService
                .getByEmail(email)
                .map(user -> new User(user.getEmail(), user.getPassword(), new ArrayList<>()))
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("User with email %s not found", email)
                        )
                );
    }
}
