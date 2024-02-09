package ro.tuc.ds2020.services.impl;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

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
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
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
