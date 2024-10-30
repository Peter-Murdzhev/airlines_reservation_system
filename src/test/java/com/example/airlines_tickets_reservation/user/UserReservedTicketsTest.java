package com.example.airlines_tickets_reservation.user;

import com.example.airlines_tickets_reservation.ticket.Ticket;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserReservedTicketsTest {
    @Autowired
    private UserReservedTickets userReservedTickets;

    @Test
    @WithMockUser(username = "steli.bach@gmail.com", roles = "USER")
    void getReservedTicketsByUser(){
        List<Ticket> ticketsByUser = userReservedTickets.getReservedTicketsByUser(18);

        assertNotNull(ticketsByUser);
    }

    @Test
    @WithMockUser(username = "stefan.dichev@abv.bg", roles = "USER")
    void tryingToAccessTicketsOfOtherUserFails(){
        assertThrows(AccessDeniedException.class,
                () ->  userReservedTickets.getReservedTicketsByUser(18));
    }
}