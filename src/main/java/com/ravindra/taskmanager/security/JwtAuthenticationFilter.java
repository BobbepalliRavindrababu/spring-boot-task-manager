package com.ravindra.taskmanager.security;

import com.ravindra.taskmanager.service.CustomUserDetailsService;
import com.ravindra.taskmanager.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService){
        this.jwtService=jwtService;
        this.customUserDetailsService=customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        String authHeader=request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=authHeader.substring(7);
        try{
            String email=jwtService.extractUsername(token);

            UserDetails userDetails=customUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }

        catch (Exception e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }

        filterChain.doFilter(request,response);
    }
}
