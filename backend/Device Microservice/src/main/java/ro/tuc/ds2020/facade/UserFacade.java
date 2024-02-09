package ro.tuc.ds2020.facade;

import ro.tuc.ds2020.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserFacade {
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> save(UserDTO deviceDTO);

    boolean delete(Long id);
}
