package com.example.userservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email is required") @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters") String email,
        @NotBlank(message = "Password is required") String password
) {
}
