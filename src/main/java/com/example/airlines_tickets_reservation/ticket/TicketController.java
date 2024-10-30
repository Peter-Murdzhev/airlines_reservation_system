package com.example.airlines_tickets_reservation.ticket;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Integer id) {
        try {
            Ticket ticket = ticketService.getTicketById(id);
            TicketDTO ticketDTO = new TicketDTO(ticket.getId(), ticket.getAirlineCompany(),
                    ticket.getAirport(), ticket.getDestination(), ticket.getDateAndTimeOfDeparture(),
                    ticket.getTicketPrice());
            return ResponseEntity.ok(ticketDTO);
        }catch (IllegalArgumentException iae){
            return new ResponseEntity<>("ticket isn't found",HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/getall")
    public ResponseEntity<List<TicketDTO>> getAllAvailableTickets() {
        List<Ticket> allTickets = ticketService.getAllTickets();
        List<TicketDTO> allTicketsDTO = allTickets.stream()
                .map(ticket -> new TicketDTO(ticket.getId(), ticket.getAirlineCompany(),
                        ticket.getAirport(), ticket.getDestination(), ticket.getDateAndTimeOfDeparture(),
                        ticket.getTicketPrice())).collect(Collectors.toList());

        return ResponseEntity.ok(allTicketsDTO);
    }

    @GetMapping(value = "/get/company/{airlineCompany}")
    public ResponseEntity<List<TicketDTO>> getTicketsByCompany(@PathVariable String airlineCompany) {
        List<Ticket> ticketsByCompany = ticketService.getTicketsByAirlineCompany(airlineCompany);
        List<TicketDTO> ticketsByCompanyDTO = ticketsByCompany.stream()
                .map(ticket -> new TicketDTO(ticket.getId(), ticket.getAirlineCompany(),
                        ticket.getAirport(), ticket.getDestination(), ticket.getDateAndTimeOfDeparture(),
                        ticket.getTicketPrice())).collect(Collectors.toList());

        return ResponseEntity.ok(ticketsByCompanyDTO);
    }

    @GetMapping(value = "/get/destination/{destination}")
    public ResponseEntity<List<TicketDTO>> getTicketByDestination(@PathVariable String destination) {
        List<Ticket> ticketsByDestination = ticketService.getTicketsByDestination(destination);
        List<TicketDTO> ticketsByDestinationDTO = ticketsByDestination.stream()
                .map(ticket -> new TicketDTO(ticket.getId(), ticket.getAirlineCompany(),
                        ticket.getAirport(), ticket.getDestination(), ticket.getDateAndTimeOfDeparture(),
                        ticket.getTicketPrice())).collect(Collectors.toList());

        return ResponseEntity.ok(ticketsByDestinationDTO);
    }

    @PostMapping(value = "/admin/add")
    public ResponseEntity<TicketDTO> addTicket(@Valid @RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.addTicket(ticket);

        TicketDTO ticketDTO = new TicketDTO(savedTicket.getId(), savedTicket.getAirlineCompany(),
                savedTicket.getAirport(), savedTicket.getDestination(),
                savedTicket.getDateAndTimeOfDeparture(), savedTicket.getTicketPrice());

        return ResponseEntity.ok(ticketDTO);
    }

    @PutMapping(value = "/admin/alter/{id}")
    public ResponseEntity<?> alterTicket(@PathVariable Integer id,
                                         @RequestBody Ticket ticket) {
        try {
            Ticket savedTicket = ticketService.alterTicket(id, ticket);

            TicketDTO ticketDTO = new TicketDTO(savedTicket.getId(), savedTicket.getAirlineCompany(),
                    savedTicket.getAirport(), savedTicket.getDestination(),
                    savedTicket.getDateAndTimeOfDeparture(), savedTicket.getTicketPrice());

            return ResponseEntity.ok(ticketDTO);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("ticket isn't found", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/admin/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.ok("ticket deleted");
    }
}
