package com.example.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.RecoveryUserLoginDto;
import com.example.api.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> postMethodName(@RequestBody RecoveryUserLoginDto recoveryUserLoginDto) {
        String token = authService.authenticate(recoveryUserLoginDto.name(), recoveryUserLoginDto.password());

        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }
    

}