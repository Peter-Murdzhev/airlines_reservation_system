package com.example.airlines_tickets_reservation.ticket;

import com.example.airlines_tickets_reservation.booked_tickets.BookedTicketService;
import com.example.airlines_tickets_reservation.user.User;
import com.example.airlines_tickets_reservation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Service
public class PassengerList {
    private final TicketService ticketService;
    private final BookedTicketService bookedTicketService;
    private final UserService userService;

    public LinkedHashMap<Integer,String> getPassengerList(Integer ticketId){
        Ticket ticket = ticketService.getTicketById(ticketId);
        boolean[] seatsTaken = ticket.getIsSeatTaken();

        LinkedHashMap<Integer,String> passengerList = new LinkedHashMap<>();

        for (int seatIndex = 0; seatIndex < seatsTaken.length; seatIndex++) {
            if(!seatsTaken[seatIndex]){
                passengerList.put(seatIndex + 1, "empty");
            }else{
                User user = userService.
                        findUserById(bookedTicketService.getUserId(ticketId,seatIndex));
               passengerList.put(seatIndex + 1, user.getFullname());
            }
        }

        return passengerList;
    }
}
