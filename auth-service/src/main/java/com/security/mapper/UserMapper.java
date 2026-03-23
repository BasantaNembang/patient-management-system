package com.security.mapper;

import com.security.dto.UserDTO;
import com.security.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setRole(user.getRole());
        dto.setAddress(user.getAddress());
        dto.setEmail(user.getEmail());
        return dto;
    }


}
