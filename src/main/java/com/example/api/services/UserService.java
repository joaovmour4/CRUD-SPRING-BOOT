package com.example.api.services;

import com.example.api.dto.CreateUserDto;
import com.example.api.dto.RecoveryUserDto;
import com.example.api.dto.RecoveryUserLoginDto;
import com.example.api.entities.User;
import com.example.api.exceptions.AuthenticationFailedException;
import com.example.api.exceptions.ResourceAlreadyExistsException;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.mappers.UserMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserMapper userMapper;

    public RecoveryUserDto createUser(CreateUserDto createUserDto){

        userRepository.findByName(createUserDto.name())
                    .ifPresent(user -> {
                        throw new ResourceAlreadyExistsException("Já existe um usuário com este nome");
                    });

        User user = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .password(passwordService.hashPassword(createUserDto.password()))
                .build();

        User userSaved = userRepository.save(user);

        return userMapper.recoveryUserToDto(userSaved);

    }

    public List<RecoveryUserDto> getUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> userMapper.recoveryUserToDto(user)).toList();
    }

    public RecoveryUserDto getUserByName(String username){
        User user = userRepository.findByName(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return userMapper.recoveryUserToDto(user);
    }

    public RecoveryUserDto authenticate(String name, String plainPassword){
        User user = userRepository.findByName(name)
                    .orElseThrow(() -> new AuthenticationFailedException("Credenciais inválidas"));

        if(!passwordService.verifyPassword(plainPassword, user.getPassword())){
            throw new AuthenticationFailedException("Credenciais inválidas");
        }
        return userMapper.recoveryUserToDto(user);
    }

}
