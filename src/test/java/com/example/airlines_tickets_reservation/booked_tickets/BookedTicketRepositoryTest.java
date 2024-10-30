package com.example.airlines_tickets_reservation.booked_tickets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookedTicketRepositoryTest {

    @Autowired
    private BookedTicketRepository bookedTicketRepository;

    @Test
    void getUserIdFromTicketIdAndSeatNum(){
        Integer userId = bookedTicketRepository.getUserIdFromTicketIdAndSeatNum(43,0);

        assertNotNull(userId);
        assertEquals(6,userId);
    }

    @Test
    void findBookedTicketsListByUserId(){
        List<BookedTicket> bookedTickets = bookedTicketRepository.findByUserId(6);

        for(BookedTicket bookedTicket : bookedTickets){
            assertEquals(6,bookedTicket.getUserId());
        }
    }
}