package com.example.consumer.facade;

import com.example.consumer.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserFacade {
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> save(UserDTO deviceDTO);

    boolean delete(Long id);
}
