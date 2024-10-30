package com.example.airlines_tickets_reservation.ticket;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TicketServiceTest {
    @Autowired
    private TicketService ticketService;

    @Test
    void ticketsByBritishAirlines(){
        List<Ticket> tickets = ticketService.getTicketsByAirlineCompany("British Airlines");

        for(Ticket ticket : tickets){
            assertEquals("british airlines",ticket.getAirlineCompany().toLowerCase());
        }
    }

    @Test
    void ticketsToParis(){
        List<Ticket> tickets = ticketService.getTicketsByDestination("paris");

        for(Ticket ticket : tickets){
            assertEquals("paris",ticket.getDestination().toLowerCase());
        }
    }

    @Test
    void ticketsToLondon(){
        List<Ticket> tickets = ticketService.getTicketsByDestination("London");

        for(Ticket ticket : tickets){
            assertEquals("London",ticket.getDestination());
        }
    }



}