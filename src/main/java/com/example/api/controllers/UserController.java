package com.example.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.CreateUserDto;
import com.example.api.dto.RecoveryUserDto;
import com.example.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<RecoveryUserDto> createUser(@RequestBody CreateUserDto createUserDto){
        return new ResponseEntity<>(userService.createUser(createUserDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecoveryUserDto>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<RecoveryUserDto> getUser(@PathVariable String name){
        RecoveryUserDto user = userService.getUserByName(name);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
