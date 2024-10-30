package com.example.airlines_tickets_reservation.ticket;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PassengerListTest {
    @Autowired
    private PassengerList passengerList;

    @BeforeEach
    void authenticate(){
        Authentication auth = new UsernamePasswordAuthenticationToken("peter_mur@gmail.com",
                null, Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void checkPlacesCountOnTicketToManchester() {
        LinkedHashMap<Integer, String> seatMapper = passengerList.getPassengerList(45);

        assertEquals(30, seatMapper.size());
    }

    @Test
    void checkPlacesCountOnTicketToPrague() {
        LinkedHashMap<Integer, String> seatMapper = passengerList.getPassengerList(54);

        assertEquals(40, seatMapper.size());
    }

    @Test
    void isSeatTaken(){
        LinkedHashMap<Integer, String> seatMapper = passengerList.getPassengerList(44);

        //suggested assertNotEquals would produce test passing when it shouldn't
        assertFalse(seatMapper.get(1).equals("empty"));
    }

}