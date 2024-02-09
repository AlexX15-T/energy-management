package com.ds.spring.p1.security.services.impl;

import com.ds.spring.p1.dtos.UserDTO;
import com.ds.spring.p1.dtos.builders.UserBuilder;
import com.ds.spring.p1.models.User;
import com.ds.spring.p1.repository.UserRepository;
import com.ds.spring.p1.security.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String username, String password) {
        AtomicBoolean logged = new AtomicBoolean(false);

        userRepository.findByUsername(username).ifPresent(user -> {
            if (user.getPassword().equals(password)) {
                LOGGER.debug("User with username {} was logged in", username);
                logged.set(true);
            }
        });

        return logged.get();
    }

    public Optional<UserDTO> findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            LOGGER.error("User with id {} was not found in db", id);
            return Optional.empty();
        }

        User user = userOptional.get();
        LOGGER.debug("User with id {} was found in db", id);

        return Optional.of(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    public Optional<UserDTO> findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            LOGGER.error("User with username {} was not found in db", username);
            return Optional.empty();
        }

        User user = userOptional.get();
        LOGGER.debug("User with username {} was found in db", username);

        return Optional.of(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOs = users.stream().map(UserBuilder::toUserDTO).collect(Collectors.toList());

        LOGGER.debug("Users were found in db");

        return userDTOs;
    }

    public Optional<UserDTO> save(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        User user = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getRoles());
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return Optional.of(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    public Optional<UserDTO> update(UserDTO userDTO) {
        Long id = userDTO.getId();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            LOGGER.error("User with id {} was not found in db", id);
            return Optional.empty();
        }

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles());
        userRepository.update(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
        LOGGER.debug("User with id {} was updated in db", id);
        return Optional.of(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    public boolean delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            LOGGER.error("User with id {} was not found in db", id);
            return false;
        }

        userRepository.delete(user);
        LOGGER.debug("User with id {} was deleted from db", id);
        return true;
    }
}
