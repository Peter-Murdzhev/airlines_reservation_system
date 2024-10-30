package com.example.airlines_tickets_reservation.auth;

import com.example.airlines_tickets_reservation.config.JwtService;
import com.example.airlines_tickets_reservation.user.User;
import com.example.airlines_tickets_reservation.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Test
    public void registerProducesValidToken(){
        RegisterRequest request = RegisterRequest.builder()
                        .fullname("Zaro Bashev")
                        .password("zxcvbn")
                        .email("zaro@gmail.com")
                        .build();

        AuthenticationResponse token = authenticationService.register(request);

        User user = userService.loadUserByUsername("zaro@gmail.com");

        assertTrue(jwtService.isTokenValid(token.getToken(),user));
    }

    @Test
    public void loginProducesValidToken(){
        AuthenticationRequest auth = AuthenticationRequest.builder()
                .email("steli.bach@gmail.com")
                .password("qwerty")
                .build();

        AuthenticationResponse token = authenticationService.authenticate(auth);

        User user = userService.loadUserByUsername("steli.bach@gmail.com");

        assertTrue(jwtService.isTokenValid(token.getToken(),user));
    }
}