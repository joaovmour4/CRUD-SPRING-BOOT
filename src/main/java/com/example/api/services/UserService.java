package com.example.api.services;

import com.example.api.dto.CreateUserDto;
import com.example.api.dto.RecoveryUserDto;
import com.example.api.entities.User;
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
    private UserMapper userMapper;

    public RecoveryUserDto createUser(CreateUserDto createUserDto){

        User findUser = userRepository.findByName(createUserDto.name());

        if(findUser != null){
            throw new RuntimeException("O nome de usuário já existe.");
        }


        User user = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .password(createUserDto.password())
                .build();

        User userSaved = userRepository.save(user);

        return userMapper.recoveryUserToDto(userSaved);

    }

    public List<RecoveryUserDto> getUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> userMapper.recoveryUserToDto(user)).toList();
    }

    public RecoveryUserDto getUserByName(String username){
        User user = userRepository.findByName(username);
        return userMapper.recoveryUserToDto(user);
    }

}
