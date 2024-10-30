package com.example.airlines_tickets_reservation.ticket;

import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TicketRepositoryTest {
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void findTicketsByDestinationParis() {
        List<Ticket> ticketsForDestination = ticketRepository.findTicketsByDestination("Paris");

       for(Ticket ticket : ticketsForDestination){
           assertEquals("Paris", ticket.getDestination());
       }

       assertEquals(2,ticketsForDestination.size());
    }

    @Test
    void findTicketsByDestinationLondon() {
        List<Ticket> ticketsForDestination = ticketRepository.findTicketsByDestination("london");

        for(Ticket ticket : ticketsForDestination){
            assertEquals("london", ticket.getDestination().toLowerCase());
        }

        assertEquals(2,ticketsForDestination.size());
    }

    @Test
    void findTicketsByBritishAirlines(){
        List<Ticket> ticketsByBritishAirlines =
                ticketRepository.findTicketsByAirlineCompany("british airlines");

        for(Ticket ticket :  ticketsByBritishAirlines){
            assertEquals("british airlines", ticket.getAirlineCompany().toLowerCase());
        }

        assertEquals(4,ticketsByBritishAirlines.size());
    }
}