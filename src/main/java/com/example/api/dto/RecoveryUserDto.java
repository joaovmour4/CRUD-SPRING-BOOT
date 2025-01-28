package com.example.api.dto;

public record RecoveryUserDto(
    Long id,
    String profileImage,
    String name,
    String email,
    String city
) {
    
}
