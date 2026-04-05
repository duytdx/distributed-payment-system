package com.example.userservice.Controller;

import com.example.userservice.DTO.LoginRequest;
import com.example.userservice.DTO.ResponseToken;
import com.example.userservice.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseToken login(@Valid @RequestBody LoginRequest request) {
        try{
            return authService.login(request);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
