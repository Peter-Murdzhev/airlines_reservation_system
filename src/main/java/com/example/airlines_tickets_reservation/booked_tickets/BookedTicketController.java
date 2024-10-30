package com.example.airlines_tickets_reservation.booked_tickets;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/ticket/reserve")
public class BookedTicketController {
    private final BookedTicketService bookedTicketService;

    @GetMapping(value = "/availableplaces/{ticketId}")
    public ResponseEntity<HashMap<Integer,Boolean>> showAvailablePlaces
            (@PathVariable Integer ticketId){
        return ResponseEntity.ok(bookedTicketService.showAvailablePlaces(ticketId));
    }

    @GetMapping(value = "/get/{id}")
    public BookedTicket getReservation(@PathVariable Integer id){
        return bookedTicketService.getReservation(id);
    }

    @PostMapping(value = "/{ticketId}/randomseat")
    public ResponseEntity<String> reserveTicketWithRandomSeat(@PathVariable Integer ticketId){
        try{
            bookedTicketService.reserveTicketWithRandomSeat(ticketId);

            return ResponseEntity.ok("ticket has been booked successfully");
        }catch (UsernameNotFoundException unnfe){
            return new ResponseEntity<>("user isn't found", HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException iae){
            return new ResponseEntity<>("required ticket isn't found",HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException ise){
            return new ResponseEntity<>("all the tickets have been booked",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/{ticketId}/seat/{chosenSeat}")
    public ResponseEntity<String> reserveTicketWithChosenSeat(@PathVariable Integer ticketId,
                                                              @PathVariable int chosenSeat){
        try {
            bookedTicketService.reserveTicketWithChosenSeat(ticketId, chosenSeat);

            return ResponseEntity.ok("ticket has been booked successfully");
        }catch (UsernameNotFoundException unnfe){
            return new ResponseEntity<>("user isn't found", HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException iae){
            return new ResponseEntity<>("required ticket isn't found",HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException ise){
            return new ResponseEntity<>("all the tickets have been booked",HttpStatus.BAD_REQUEST);
        }catch (TakenSeatException tse){
            return new ResponseEntity<>("The chosen seat is already booked",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Integer id){
        try{
            bookedTicketService.cancelReservation(id);
            return ResponseEntity.ok("reservation has been cancelled");
        }catch (IllegalArgumentException iae){
            return new ResponseEntity<>("reservation with this ID isn't found",
                    HttpStatus.BAD_REQUEST);
        }catch (AccessDeniedException ade){
            return new ResponseEntity<>("you can't delete this reservation",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
