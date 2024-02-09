package com.ds.spring.p1.controllers;

import com.ds.spring.p1.dtos.RoleDTO;
import com.ds.spring.p1.dtos.UserDTO;
import com.ds.spring.p1.facade.RoleFacade;
import com.ds.spring.p1.facade.UserFacade;
import com.ds.spring.p1.models.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin( origins = {"http://localhost:3000", "http://localhost:8080"}, maxAge = 3600)
@RequestMapping(value = "/api/users")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserFacade userFacade;

    private final RoleFacade roleFacade;

    @Autowired
    public UserController(UserFacade userFacade, RoleFacade roleFacade) {
        this.userFacade = userFacade;
        this.roleFacade = roleFacade;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long userId) {
        Optional<UserDTO> dto = userFacade.findUserById(userId);
        return dto.map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<RoleDTO>> getRoles() {
        List<RoleDTO> roles = roleFacade.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userFacade.getUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> insertUser(@Valid @RequestBody UserDTO userDTO) {
        Optional<UserDTO> dto = userFacade.save(userDTO);

        return dto.map(id -> new ResponseEntity<>(id.getId(), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Long> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(userId);
        Optional<UserDTO> dto = userFacade.update(userDTO);

        return dto.map(id -> new ResponseEntity<>(id.getId(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long userId) {
        boolean dto = userFacade.delete(userId);

        return dto ? new ResponseEntity<>(userId, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
