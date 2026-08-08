package com.ravindra.taskmanager.security;

import com.ravindra.taskmanager.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//UserDetails interface provided by Spring Security by adding the dependency, ctrl+B on UserDetails, you will see it structure

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    //In Spring Security, "username" means the login identifier, not necessarily a field named username.
    public String getUsername(){
        return user.getEmail();
    }

    @Override
    //This method return encoded password
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(
                new SimpleGrantedAuthority(user.getRole())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }



}
