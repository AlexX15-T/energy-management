package com.ds.spring.p1.dtos.builders;

import com.ds.spring.p1.dtos.UserDTO;
import com.ds.spring.p1.models.User;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class UserBuilder {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles());
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(userDTO.getUsername(),userDTO.getEmail(), userDTO.getPassword(),  userDTO.getRoles());
    }
}
