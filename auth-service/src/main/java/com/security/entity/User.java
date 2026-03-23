package com.security.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="user")
public class User {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String role;


}


