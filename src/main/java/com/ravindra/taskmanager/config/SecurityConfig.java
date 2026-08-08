package com.ravindra.taskmanager.config;

import com.ravindra.taskmanager.entity.User;
import com.ravindra.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests
                        (auth->auth
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()
                );
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner createTestUser(UserRepository userRepository, PasswordEncoder passwordEncoder){

        return args->{
            if(userRepository.findByEmail("ravi@gmail.com").isEmpty()){

                User user=new User();

                user.setEmail("rai@gmail.com");
                user.setUsername("ravi");
                user.setPassword(passwordEncoder.encode("ravi123"));
                user.setRole("ROLE_USER");
                user.setEnabled(true);

                userRepository.save(user);

                System.out.println("Test user is created successfully");
            }
        };
    }
}
