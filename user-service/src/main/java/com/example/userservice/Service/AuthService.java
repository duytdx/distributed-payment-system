package com.example.userservice.Service;

import com.example.userservice.DTO.LoginRequest;
import com.example.userservice.DTO.RegisterRequest;
import com.example.userservice.DTO.ResponseToken;
import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.config.JwtUntil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUntil jwtUntil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUntil jwtUntil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUntil = jwtUntil;
    }

    public ResponseToken login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUntil.generateToken(user.getId(), user.getName(), user.getRole());

        return new ResponseToken(token, jwtUntil.getExpiration());
    }

    public User register(RegisterRequest request) {

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");

        return userRepository.save(user);
    }
}
