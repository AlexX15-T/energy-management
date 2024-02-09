package com.ds.spring.p1.security.services;


import com.ds.spring.p1.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean login(String username, String password);

    Optional<UserDTO> findUserById(Long id);

    Optional<UserDTO> findUserByUsername(String username);

    List<UserDTO> getUsers();

    Optional<UserDTO> save(UserDTO userDTO);

    Optional<UserDTO> update(UserDTO userDTO);

    boolean delete(Long id);
}
