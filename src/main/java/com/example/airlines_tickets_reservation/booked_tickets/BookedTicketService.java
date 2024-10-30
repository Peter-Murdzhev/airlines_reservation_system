package com.example.airlines_tickets_reservation.booked_tickets;

import com.example.airlines_tickets_reservation.ticket.Ticket;
import com.example.airlines_tickets_reservation.ticket.TicketService;
import com.example.airlines_tickets_reservation.user.User;
import com.example.airlines_tickets_reservation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
//get requests are implemented in UserReservedTickets and PassengerList
public class BookedTicketService {
    private final UserService userService;
    private final TicketService ticketService;
    private final BookedTicketRepository bookedTicketRepository;

    //In the ticket entity I have isSeatTaken because by default I will get false values
    // which makes the work easy
    //Here I'm reversing the boolean value to display the free seats
    public HashMap<Integer, Boolean> showAvailablePlaces(Integer ticketId){
        boolean[] places = ticketService.getTicketById(ticketId).getIsSeatTaken();
        HashMap<Integer,Boolean> placesMapper = new HashMap<>();

        for (int i = 0; i < places.length; i++) {
            placesMapper.put(i + 1,!places[i]);
        }

        return placesMapper;
    }

    public BookedTicket getReservation(Integer id){
        return  bookedTicketRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("reservation isn't found."));
    }

    public void reserveTicketWithRandomSeat(Integer ticketId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.loadUserByUsername(authentication.getName());

        Ticket ticket = ticketService.getTicketById(ticketId);

        if(ticket.getTicketsSold() < ticket.getTicketsCount()){
            int reservedSeat = ticket.generateRandomSeat();

            BookedTicket bookedTicket = BookedTicket.builder()
                    .userId(user.getId())
                    .ticketId(ticket.getId())
                    .seatIndex(reservedSeat)
                    .build();

            boolean[] seats = ticket.getIsSeatTaken();
            seats[reservedSeat] = true;
            ticket.setIsSeatTaken(seats);
            ticket.setTicketsSold(ticket.getTicketsSold() + 1);
            ticketService.alterTicket(ticket.getId(),ticket);

            bookedTicketRepository.save(bookedTicket);
        }else{
            throw new IllegalStateException("all tickets are already booked");
        }
    }

    public void reserveTicketWithChosenSeat(Integer ticketId, int  chosenSeat){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.loadUserByUsername(authentication.getName());

        Ticket ticket = ticketService.getTicketById(ticketId);

        if(ticket.getTicketsSold() < ticket.getTicketsCount()){
            if(ticket.getIsSeatTaken()[chosenSeat - 1]){
                throw new TakenSeatException("The chosen seat is already booked");
            }

            BookedTicket bookedTicket = BookedTicket.builder()
                    .userId(user.getId())
                    .ticketId(ticket.getId())
                    .seatIndex(chosenSeat - 1)
                    .build();

            boolean[] seats = ticket.getIsSeatTaken();
            seats[chosenSeat - 1] = true;
            ticket.setIsSeatTaken(seats);
            ticket.setTicketsSold(ticket.getTicketsSold() + 1);
            ticketService.alterTicket(ticket.getId(),ticket);

            bookedTicketRepository.save(bookedTicket);
        }else{
            throw new IllegalStateException("all tickets are already booked");
        }
    }

    public void cancelReservation(Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        BookedTicket bookedTicket = bookedTicketRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("reservation isn't found"));

        User user = userService.loadUserByUsername(authentication.getName());

        if(user.getId() != bookedTicket.getUserId()){
            throw new AccessDeniedException("You cant delete this reservation");
        }

        Ticket ticket = ticketService.getTicketById(bookedTicket.getTicketId());
        boolean[] seats = ticket.getIsSeatTaken();
        seats[bookedTicket.getSeatIndex()] = false;
        ticket.setIsSeatTaken(seats);
        ticket.setTicketsSold(ticket.getTicketsSold() - 1);
        ticketService.alterTicket(ticket.getId(),ticket);

        bookedTicketRepository.delete(bookedTicket);
    }

    public List<BookedTicket> findAllByUserId(Integer userId){
        return bookedTicketRepository.findByUserId(userId);
    }

    public Integer getUserId(Integer ticketId,int seatNumber){
        return bookedTicketRepository.getUserIdFromTicketIdAndSeatNum(ticketId,seatNumber);
    }
}
