package com.ravindra.taskmanager.controller;

import com.ravindra.taskmanager.dto.LoginRequest;
import com.ravindra.taskmanager.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,JwtService jwtService){
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
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

}
