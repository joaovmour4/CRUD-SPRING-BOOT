package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.RecoveryUserDto;


@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public String authenticate(String username, String password){
        RecoveryUserDto user = userService.authenticate(username, password);
        
        return tokenService.generateToken(user);

    } 

}
