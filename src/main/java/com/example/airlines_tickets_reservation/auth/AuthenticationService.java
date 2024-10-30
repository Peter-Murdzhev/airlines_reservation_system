package com.example.airlines_tickets_reservation.auth;

import com.example.airlines_tickets_reservation.config.JwtService;
import com.example.airlines_tickets_reservation.user.AppUserRole;
import com.example.airlines_tickets_reservation.user.User;
import com.example.airlines_tickets_reservation.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .fullname(request.getFullname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(AppUserRole.USER)
                .build();

        repository.save(user);
        String jwtoken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtoken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("username not found"));

        String jwtoken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtoken).build();
    }
}
