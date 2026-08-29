package com.ravindra.taskmanager.config;

import com.ravindra.taskmanager.entity.User;
import com.ravindra.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.findByEmail("admin@taskmanager.com").isEmpty()) {

            User admin = new User();

            admin.setUsername("admin");
            admin.setEmail("admin@taskmanager.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);

            userRepository.save(admin);

            System.out.println("Initial ADMIN user created successfully.");
        }
    }
}