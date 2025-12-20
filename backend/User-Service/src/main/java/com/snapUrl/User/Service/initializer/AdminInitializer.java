package com.snapUrl.User.Service.initializer;

import com.snapUrl.User.Service.entities.UserEntity;
import com.snapUrl.User.Service.enums.Role;
import com.snapUrl.User.Service.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        boolean adminExists = userRepo.existsByRole(Role.ADMIN);

        if (!adminExists) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin@123"));
            admin.setRole(Role.ADMIN);
            userRepo.save(admin);
        }
    }
}

