package ro.tuc.ds2020.facade.impl;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.facade.UserFacade;
import ro.tuc.ds2020.services.UserService;

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
