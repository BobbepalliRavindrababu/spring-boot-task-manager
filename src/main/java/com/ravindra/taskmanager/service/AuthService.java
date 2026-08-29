package com.ravindra.taskmanager.service;

import com.ravindra.taskmanager.dto.RegisterRequest;
import com.ravindra.taskmanager.dto.RegisterResponse;
import com.ravindra.taskmanager.entity.User;
import com.ravindra.taskmanager.exception.EmailAlreadyExistsException;
import com.ravindra.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public  RegisterResponse userRegister(RegisterRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("This Email id user is already exist");
        }

        User user=new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        return new RegisterResponse(
                user.getUsername(),
                user.getEmail(),
                "User registered successfully"
        );
    }
}

