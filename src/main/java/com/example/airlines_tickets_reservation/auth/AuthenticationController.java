package com.example.airlines_tickets_reservation.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    //this api is for registering a new user
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }

            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    //this api is for authenticating existing user (log in)
    public ResponseEntity<?> authenticateRequest(
            @RequestBody AuthenticationRequest request){
        try {
            AuthenticationResponse authResponse = authService.authenticate(request);
            return ResponseEntity.ok(authResponse);
        }catch (AuthenticationException ae){
            Map<String,String> error = new HashMap<>();
            error.put("authentication","Username or password incorrect!");

            return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
        }
    }
}
