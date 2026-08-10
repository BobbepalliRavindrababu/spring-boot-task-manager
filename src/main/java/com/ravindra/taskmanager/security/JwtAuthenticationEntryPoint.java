package com.ravindra.taskmanager.security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.naming.AuthenticationException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{
//        response.sendError(
//                HttpServletResponse.SC_UNAUTHORIZED,
//                "Unauthorized"
//        );
//    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws java.io.IOException, ServletException {
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized"
        );
    }
}
