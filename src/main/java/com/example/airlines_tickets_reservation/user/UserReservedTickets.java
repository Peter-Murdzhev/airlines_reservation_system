package com.example.airlines_tickets_reservation.user;

import com.example.airlines_tickets_reservation.booked_tickets.BookedTicket;
import com.example.airlines_tickets_reservation.booked_tickets.BookedTicketService;
import com.example.airlines_tickets_reservation.ticket.Ticket;
import com.example.airlines_tickets_reservation.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserReservedTickets {
    private final UserService userService;
    private final BookedTicketService bookedTicketService;
    private final TicketService ticketService;

    public List<Ticket> getReservedTicketsByUser(Integer userId){
        User user = userService.findUserById(userId);

        if(userService.isAdmin()){
            return extractTickets(user);
        }

        if(userService.doesUsernameMatchTheToken(user)){
            return extractTickets(user);
        }else{
            throw new AccessDeniedException("You can't see the booked tickets of another user");
        }
    }

    private List<Ticket> extractTickets(User user){
        List<BookedTicket> reservedTickets = bookedTicketService.findAllByUserId(user.getId());
        List<Ticket> tickets = new ArrayList<>();

        for(BookedTicket bookedTicket : reservedTickets){
            tickets.add(ticketService.getTicketById(bookedTicket.getTicketId()));
        }

        return tickets;
    }
}
