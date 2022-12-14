package io.submanager.service;

import io.submanager.exception.ClientException;
import io.submanager.model.entity.User;
import io.submanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User, Integer, UserRepository> {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.create(user);
    }

    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User getUser(Principal principal) {
        String email = principal.getName();
        Optional<User> user = getByEmail(email);
        return user.orElseThrow(() -> new ClientException("User does not exists"));
    }
}
