package com.example.demo.utils;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.exception.UserNotFoundException;
import com.example.demo.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class LoggedInUser {
    private final UserRepository userRepository;

    public User get() {
        //utilizatorul autentificat, salvat in SpringSecurity in JwtAuthenticationFilter pentru requestul curent
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = new String(Base64.getDecoder().decode(authentication.getName()));

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}
