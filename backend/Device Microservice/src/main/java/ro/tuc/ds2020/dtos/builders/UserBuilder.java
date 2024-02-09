package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.User;

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
