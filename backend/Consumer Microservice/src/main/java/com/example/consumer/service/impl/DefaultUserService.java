package com.example.consumer.service.impl;

import com.example.consumer.dto.UserDTO;
import com.example.consumer.dto.builder.UserBuilder;
import com.example.consumer.entity.User;
import com.example.consumer.repository.UserRepository;
import com.example.consumer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = userRepository.findAll().stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());

        return userDTOList;
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        Optional <User> user  = userRepository.findById(id);

        if(!user.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
        }

        return Optional.of(UserBuilder.toUserDTO(user.get()));
    }

    @Override
    public Optional<UserDTO> save(UserDTO deviceDTO) {
        User user = UserBuilder.toEntity(deviceDTO);
        user = userRepository.save(user);
        return Optional.of(UserBuilder.toUserDTO(user));
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
