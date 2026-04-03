package com.example.userservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRequest(
        @NotBlank(message = "Name is required") @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters") String name,
        @NotBlank(message = "Email is required") @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters") String email,
        @NotBlank(message = "Password is required") String password,
        @NotBlank(message = "Role is required") String role) {
}
