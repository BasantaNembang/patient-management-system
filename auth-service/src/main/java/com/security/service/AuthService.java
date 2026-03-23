package com.security.service;


import com.security.dto.LogInRequestDTO;
import com.security.dto.SignUpRequestDTO;
import com.security.dto.UserDTO;

import java.util.List;


public interface AuthService {

    String singUp(SignUpRequestDTO dto);

    boolean authenticateUser(LogInRequestDTO dto);

    boolean validateToken(String substring);

    void deleteUser(String email);

    List<UserDTO> getAllUsers();
}
