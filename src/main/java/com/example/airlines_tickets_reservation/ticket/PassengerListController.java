package com.example.airlines_tickets_reservation.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PassengerListController {
    private final PassengerList passengerList;

    @GetMapping(value = "/ticket/admin/{ticketId}/passengerlist")
    public ResponseEntity<?> getPassengerList(@PathVariable Integer ticketId) {
        try {
            return ResponseEntity.ok(passengerList.getPassengerList(ticketId));
        } catch (AccessDeniedException ade) {
            return new ResponseEntity<>("Only ADMIN can see the passenger list",
                    HttpStatus.FORBIDDEN);
        }
    }
}
