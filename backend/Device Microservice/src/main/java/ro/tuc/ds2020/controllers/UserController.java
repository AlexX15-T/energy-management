package ro.tuc.ds2020.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.facade.UserFacade;

import java.util.Optional;

@RestController
@CrossOrigin( origins = {"http://localhost:3000", "http://localhost:8080"}, maxAge = 3600, allowCredentials = "true")
@RequestMapping( value = "api/users" )
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userFacade.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        Optional<UserDTO> userDTO = userFacade.findById(id);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userFacade.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> insertUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userFacade.save(userDTO));
    }
}
