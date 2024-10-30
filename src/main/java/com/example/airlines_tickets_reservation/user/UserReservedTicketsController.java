package com.example.airlines_tickets_reservation.user;

import com.example.airlines_tickets_reservation.ticket.Ticket;
import com.example.airlines_tickets_reservation.ticket.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserReservedTicketsController {
    private final UserReservedTickets userReservedTickets;

    @GetMapping(value = "/api/v1/user/reservedtickets/{userId}")
    public ResponseEntity<?> findReservedTicketsByUser(@PathVariable Integer userId){
        try {
            List<Ticket> tickets = userReservedTickets.getReservedTicketsByUser(userId);
            List<TicketDTO> ticketsDTO = tickets.stream()
                    .map(ticket -> new TicketDTO(ticket.getId(), ticket.getAirlineCompany(),
                            ticket.getAirport(),ticket.getDestination(),ticket.getDateAndTimeOfDeparture(),
                            ticket.getTicketPrice())).collect(Collectors.toList());

            return ResponseEntity.ok(ticketsDTO);
        }catch (AccessDeniedException ade){
            return new ResponseEntity<>("You are not authorized to see the list",
                    HttpStatus.FORBIDDEN);
        }
    }
}
