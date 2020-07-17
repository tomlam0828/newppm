package io.task.dailytask.services;

import io.task.dailytask.domain.User;
import io.task.dailytask.exceptions.UsernameAlreadyExistsException;
import io.task.dailytask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //username has to be unique (need exception)
            newUser.setUsername(newUser.getUsername());

            // make sure password and confirmpassword is the same


            // we dont persist or show the confirmpassword
            newUser.setConfirmPassword("");

            return userRepository.save(newUser);
        } catch(Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
    }
}
