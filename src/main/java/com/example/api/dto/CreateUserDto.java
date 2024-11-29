package com.example.api.dto;

public record CreateUserDto(
    Long id,
    String name,
    String email,
    String password
) {
    
}
