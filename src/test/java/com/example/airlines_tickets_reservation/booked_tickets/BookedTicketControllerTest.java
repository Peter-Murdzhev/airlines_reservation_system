package com.example.airlines_tickets_reservation.booked_tickets;

import com.example.airlines_tickets_reservation.auth.AuthenticationService;
import com.example.airlines_tickets_reservation.config.JwtService;
import com.example.airlines_tickets_reservation.ticket.PassengerList;
import com.example.airlines_tickets_reservation.ticket.Ticket;
import com.example.airlines_tickets_reservation.ticket.TicketService;
import com.example.airlines_tickets_reservation.user.UserReservedTickets;
import com.example.airlines_tickets_reservation.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class BookedTicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookedTicketService bookedTicketService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserReservedTickets userReservedTickets;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private PassengerList passengerList;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "palov@abv.bg", roles = "USER")
    void reserveTicketRandomSeatSuccess() throws Exception {
        when(ticketService.addTicket(any())).thenReturn(new Ticket(1,"Wiz Air",
                "Burgas airport", "Madrid",
                LocalDateTime.of(2024,11,12,15,10),
                500,100));

        mockMvc.perform
                (MockMvcRequestBuilders.post("/api/v1/ticket/reserve/{ticketId}/randomseat"
                ,1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("ticket has been booked successfully"));
    }

}