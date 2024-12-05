package com.example.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateUserDto(
    Long id,

    @Valid
    @NotBlank
    @Size(min = 5)
    String name,

    @Valid
    @NotBlank
    @Email
    String email,

    @Valid
    @NotBlank
    @Size(min = 8)
    String password
) {
    
}
