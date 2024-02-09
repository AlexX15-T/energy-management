package com.example.consumer.dto.builder;

import com.example.consumer.dto.UserDTO;
import com.example.consumer.entity.User;


public class UserBuilder {
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId());
    }

    public static User toEntity(UserDTO userDTO) {
        if(userDTO.getId() == null) {
            return new User();
        }
        return new User(userDTO.getId());
    }
}
