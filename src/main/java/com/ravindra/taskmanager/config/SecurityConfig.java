package com.ravindra.taskmanager.config;

import com.ravindra.taskmanager.entity.User;
import com.ravindra.taskmanager.repository.UserRepository;
import com.ravindra.taskmanager.security.JwtAuthenticationEntryPoint;
import com.ravindra.taskmanager.security.JwtAuthenticationFilter;
import com.ravindra.taskmanager.service.CustomUserDetailsService;
import com.ravindra.taskmanager.service.JwtService;
import jakarta.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception{

        http.
               csrf(csrf->csrf.disable())
                .authorizeHttpRequests
                        (auth->auth
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
                );

        http.exceptionHandling(exception->exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint));

        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuratrion) throws Exception{
        return configuratrion.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService){
        return new JwtAuthenticationFilter(jwtService,customUserDetailsService);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }
}
