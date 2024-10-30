package com.example.airlines_tickets_reservation.ticket;

import com.example.airlines_tickets_reservation.config.JwtService;
import com.example.airlines_tickets_reservation.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(TicketController.class)
@AutoConfigureMockMvc(addFilters = true)
@ExtendWith(MockitoExtension.class)
class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "stefan.dichev@abv.bg",authorities = "ROLE_USER")
    void getTicketsByAirlineCompany() throws Exception {
        when(ticketService.getTicketsByAirlineCompany("British Airlines")).thenReturn(
                Arrays.asList(new Ticket(1,"British Airlines", "Varna",
                        "London",
                                LocalDateTime.of(2024,9,16,16,25),
                        600,40),
                        new Ticket(2,"British Airlines", "Sofia",
                                "Manchester",
                                LocalDateTime.of(2024,9,27,12,0),
                                600,40)
        ));

       mockMvc.perform(MockMvcRequestBuilders.get("/ticket/get/company/{airlineCompany}",
                               "British Airlines")
       ).andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(print())
               .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].airlineCompany")
                       .value("British Airlines"));
    }

    @Test
    @WithMockUser(username = "ivailo.topov@gmail.com", roles = "USER")
    void getTicketsByDestination() throws Exception{
        when(ticketService.getTicketsByDestination("Paris")).thenReturn(
                Arrays.asList(new Ticket(1,"Air France", "Sofia",
                                "Paris",
                                LocalDateTime.of(2024,9,16,16,25),
                                600,40),
                        new Ticket(2,"Air France", "Sofia",
                                "Paris",
                                LocalDateTime.of(2024,9,27,12,0),
                                550,50)
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/ticket/get/destination/{destination}",
                "Paris"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination")
                        .value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].destination")
                        .value("Paris"));
    }

}