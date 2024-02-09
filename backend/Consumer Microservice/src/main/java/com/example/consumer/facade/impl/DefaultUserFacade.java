package com.example.consumer.facade.impl;

import com.example.consumer.dto.UserDTO;
import com.example.consumer.facade.UserFacade;
import com.example.consumer.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultUserFacade implements UserFacade {

    private final UserService userService;

    public DefaultUserFacade(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userService.findById(id);
    }

    @Override
    public Optional<UserDTO> save(UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @Override
    public boolean delete(Long id) {
        return userService.delete(id);
    }
}
