package com.example.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record EditUserDto(
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
    @Size(min = 2)
    String city
) {
    
}
