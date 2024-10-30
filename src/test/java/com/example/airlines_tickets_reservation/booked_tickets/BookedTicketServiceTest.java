package com.example.airlines_tickets_reservation.booked_tickets;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookedTicketServiceTest {

    @Autowired
    private BookedTicketService bookedTicketService;

    @Test
    void checkPlacesCount(){
        HashMap<Integer, Boolean> placesMapper = bookedTicketService.showAvailablePlaces(44);

        assertEquals(40,placesMapper.size());
    }

    @Test
    void ArePlacesAvailable(){
        HashMap<Integer, Boolean> placesMapper = bookedTicketService.showAvailablePlaces(44);

        assertTrue(placesMapper.get(5));
        assertTrue(placesMapper.get(27));
    }

    @Test
    void IsPlaceTaken(){
        HashMap<Integer, Boolean> placesMapper = bookedTicketService.showAvailablePlaces(44);

        assertFalse(placesMapper.get(1));
    }

    @Test
    void reserveTicketWithRandomSeatNotAuthenticatedFails(){
        assertThrows(NullPointerException.class,
                () -> bookedTicketService.reserveTicketWithRandomSeat(48));
    }

    @Test
    @WithMockUser(value = "peter.mur@gmail.com",roles = "ADMIN")
    void reserveTicketWithChosenSeatThrowsSeatTakenException(){
        assertThrows(TakenSeatException.class,
                () -> bookedTicketService.reserveTicketWithChosenSeat(44,1));
    }

}