package com.example.airlines_tickets_reservation.user;

import com.example.airlines_tickets_reservation.auth.AuthenticationService;
import com.example.airlines_tickets_reservation.booked_tickets.BookedTicketService;
import com.example.airlines_tickets_reservation.config.JwtAuthenticationFilter;
import com.example.airlines_tickets_reservation.config.SecurityConfiguration;
import com.example.airlines_tickets_reservation.ticket.PassengerList;
import com.example.airlines_tickets_reservation.ticket.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;

@WebMvcTest
@AutoConfigureMockMvc()
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserReservedTickets userReservedTickets;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private PassengerList passengerList;

    @MockBean
    private BookedTicketService bookedTicketService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "steli.bach@gmail.com", roles = "USER")
    void checkGetById() throws Exception {
        when(userService.findUserById(18)).thenReturn(
                new User(18,"Stela Bacheva",
                "steli.bach@gmail.com","qwerty",AppUserRole.USER));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/get/id/{id}",18))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value("steli.bach@gmail.com"));
    }
}