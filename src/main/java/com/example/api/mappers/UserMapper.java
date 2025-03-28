package com.example.api.mappers;

import org.mapstruct.Mapper;

import com.example.api.dto.RecoveryFindUserDto;
import com.example.api.dto.RecoveryUserDto;
import com.example.api.dto.RecoveryUserLoginDto;
import com.example.api.entities.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    RecoveryUserDto recoveryUserToDto(User user);

    RecoveryFindUserDto recoveryFindUser(User user);

    RecoveryUserLoginDto recoveryUserLoginDto(User user);
    
}
