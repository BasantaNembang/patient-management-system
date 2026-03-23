package com.security.service;


import com.security.dto.LogInRequestDTO;
import com.security.dto.SignUpRequestDTO;
import com.security.dto.UserDTO;
import com.security.entity.User;
import com.security.exception.EmailAlreadyExistException;
import com.security.exception.UserNotFoundException;
import com.security.mapper.UserMapper;
import com.security.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepo userRepo, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    private void checkIfUserAlreadyExist(String email) {
        if(userRepo.findByEmail(email).isPresent()){
            throw new EmailAlreadyExistException("User already logged-IN");
       }
    }


    @Override
    public String singUp(SignUpRequestDTO dto) {

        checkIfUserAlreadyExist(dto.getEmail());

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setAddress(dto.getAddress());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole().toUpperCase());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User dbUser =  userRepo.save(user);

        return dbUser.getEmail();
    }


    @Override
    public boolean authenticateUser(LogInRequestDTO dto) {
        return userRepo.findByEmail(dto.getEmail())
                .map(u -> passwordEncoder.matches(dto.getPassword(), u.getPassword()))
                .orElse(false);
    }

    @Override
    public boolean validateToken(String jwtToken) {
        try{
            jwtService.validateToken(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    @Override
    public void deleteUser(String email) {
       User user =  userRepo.findByEmail(email)
               .orElseThrow(()->new UserNotFoundException("no such user founded"));
       userRepo.delete(user);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }



}
