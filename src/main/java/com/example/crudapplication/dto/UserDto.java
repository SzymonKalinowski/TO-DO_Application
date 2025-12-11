package com.example.crudapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public @Data class UserDto {
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String role;
}
