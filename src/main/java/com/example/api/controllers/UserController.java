package com.example.api.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.dto.CreateUserDto;
import com.example.api.dto.EditUserDto;
import com.example.api.dto.RecoveryUserDto;
import com.example.api.services.UserService;

import ai.onnxruntime.OrtException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<RecoveryUserDto> createUser(@RequestBody @Valid CreateUserDto createUserDto){
        return new ResponseEntity<>(userService.createUser(createUserDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecoveryUserDto> editUser(@PathVariable Long id, @RequestBody @Valid EditUserDto editUserDto){
        return new ResponseEntity<>(userService.editUser(id, editUserDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RecoveryUserDto>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RecoveryUserDto> getUser(@PathVariable String name){
        RecoveryUserDto user = userService.getUserByName(name);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RecoveryUserDto> getUserById(@PathVariable Long id){
        RecoveryUserDto user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<RecoveryUserDto> setProfileImageByUserId(@RequestParam MultipartFile file, @PathVariable Long id) throws OrtException, IOException {
        RecoveryUserDto user = userService.sendProfileImage(file, id);
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    

}
