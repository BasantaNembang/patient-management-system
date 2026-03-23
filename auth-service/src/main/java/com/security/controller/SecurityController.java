package com.security.controller;


import com.security.dto.AuthResponse;
import com.security.dto.LogInRequestDTO;
import com.security.dto.SignUpRequestDTO;
import com.security.dto.UserDTO;
import com.security.exception.UserNotFoundException;
import com.security.service.AuthServiceImpl;
import com.security.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class SecurityController {

    private final AuthServiceImpl authService;
    private final JwtService jwtService;

    public SecurityController(AuthServiceImpl authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService  = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDTO dto){
        String email = authService.singUp(dto);
        if(email==null){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("User already exist");
        }
        String jwtToken = jwtService.getJwtToken(email, dto.getRole());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponse(jwtToken, dto.getRole()));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LogInRequestDTO dto){

        if(!authService.authenticateUser(dto)){
            throw new UserNotFoundException("Invalid email or password");
        }
        String jwtToken = jwtService.getJwtToken(dto.getEmail(), dto.getRole());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponse(jwtToken, dto.getRole()));
    }


    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization")
                                                  String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }


    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(authService.getAllUsers());
    }



    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email){
        authService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }




}
