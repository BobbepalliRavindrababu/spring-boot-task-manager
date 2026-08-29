package com.ravindra.taskmanager.controller;

import com.ravindra.taskmanager.dto.LoginRequest;
import com.ravindra.taskmanager.dto.RegisterRequest;
import com.ravindra.taskmanager.dto.RegisterResponse;
import com.ravindra.taskmanager.service.AuthService;
import com.ravindra.taskmanager.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private AuthService authService;

    public AuthController(AuthenticationManager authenticationManager,JwtService jwtService,AuthService authService){
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
        this.authService=authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){

        System.out.println("LOGIN CONTROLLER REACHED");
        System.out.println("EMAIL = " + loginRequest.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("AUTHENTICATION SUCCESSFUL");

        return jwtService.generateToken(loginRequest.getEmail());
    }

    @PostMapping("/register")

    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse= authService.userRegister(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);


    }

}
