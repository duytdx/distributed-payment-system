package com.example.userservice.Seeder;

import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createIfAbsent("admin", "admin@example.com", "ADMIN");
        createIfAbsent("tester", "tester@example.com", "TESTER");
    }

    private void createIfAbsent(String name, String email, String role) {
        if (userRepository.findByEmail(email).isPresent()) {
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode("12345"));

        userRepository.save(user);
    }
}


