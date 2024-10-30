package com.example.airlines_tickets_reservation.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmail(){
        User user = userRepository.findByEmail("peter.mur@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        assertEquals("peter.mur@gmail.com",user.getEmail());
    }

    @Test
    void checkIfEmailExist(){
        boolean doesEmailExist = userRepository.existsByEmail("stefan.dichev@abv.bg");

        assertTrue(doesEmailExist);
    }

    @Test
    void checkIfEmailNotExist(){
        boolean doesEmailExist = userRepository.existsByEmail("stamat_georgiev@gmail.com");

        assertFalse(doesEmailExist);
    }

}