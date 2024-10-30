package com.example.airlines_tickets_reservation.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsername(){
        User user = userService.loadUserByUsername("peter.mur@gmail.com");

        assertNotNull(user);
    }

    @Test
    void loadUserByUsernameThrowsException(){
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("mike.tyson@gmail.com"));
    }

    @Test
    @WithMockUser(username = "peter.mur@gmail.com",authorities = "ADMIN")
    void findUserById(){
        User user = userService.findUserById(20);

        assertNotNull(user);
    }

    @Test
    @WithMockUser(username = "stefan.dichev@abv.bg", roles = "USER")
    void findUserByIdFailsWhenUserTriesToAccesOtherUser(){
        assertThrows(AccessDeniedException.class,() -> userService.findUserById(6));
    }

    @Test
    @WithMockUser(username = "g.pancheva@gmail.com", roles = "USER")
    void deletingOtherUserFails(){
        assertThrows(AccessDeniedException.class,() -> userService.deleteById(17));
    }
}