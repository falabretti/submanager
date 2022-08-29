package io.submanager.service;

import io.submanager.model.entity.User;
import io.submanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
}
