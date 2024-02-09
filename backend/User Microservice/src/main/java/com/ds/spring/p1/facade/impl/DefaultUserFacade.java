package com.ds.spring.p1.facade.impl;

import com.ds.spring.p1.dtos.UserDTO;
import com.ds.spring.p1.facade.UserFacade;
import com.ds.spring.p1.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultUserFacade implements UserFacade {

    private final UserService userService;

    @Autowired
    public DefaultUserFacade(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean login(String username, String password) {
        return userService.login(username, password);
    }

    @Override
    public Optional<UserDTO> findUserById(Long id) {
        return userService.findUserById(id);
    }

    @Override
    public Optional<UserDTO> findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @Override
    public Optional<UserDTO> save(UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @Override
    public Optional<UserDTO> update(UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @Override
    public boolean delete(Long id) {
        return userService.delete(id);
    }
}
