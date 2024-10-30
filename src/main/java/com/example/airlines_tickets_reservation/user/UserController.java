package com.example.airlines_tickets_reservation.user;

import com.example.airlines_tickets_reservation.auth.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/get/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try {
            User user = userService.findUserById(id);
            UserDTO userDTO = new UserDTO(user.getId(),user.getFullname(),user.getEmail());

            return ResponseEntity.ok(userDTO);
        }catch (AccessDeniedException ade){
            String message = "you can't access other user's information";

            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/alter/{id}")
    public ResponseEntity<?> alterUser(@PathVariable Integer id,@Valid @RequestBody RegisterRequest changedUser,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
           Map<String,String> errors = new HashMap<>();

           for(FieldError fieldError : bindingResult.getFieldErrors()){
               errors.put(fieldError.getField(),fieldError.getDefaultMessage());
           }

           return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }

        try {
            return ResponseEntity.ok(userService.alterUser(id, changedUser));
        }catch (AccessDeniedException ade){
            String message = "you can't alter other user's information";

            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }catch (IllegalArgumentException iae){
            String message = "email is already taken";

            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete/id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("deleted");
        }catch (AccessDeniedException ade){
            String message = "you can't delete another user";

            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }catch (UsernameNotFoundException unnfe){
            String message = "You chose to delete a user that doesn't exist";

            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
    }
}
