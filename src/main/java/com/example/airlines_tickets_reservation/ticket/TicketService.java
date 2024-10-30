package com.example.airlines_tickets_reservation.ticket;

import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public Ticket getTicketById(Integer id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);

        return ticket.orElseThrow(() -> new IllegalArgumentException("ticket isn't found"));
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll().stream().filter(
                ticket -> ticket.getDateAndTimeOfDeparture().isAfter(LocalDateTime.now())
        ).collect(Collectors.toList());
    }

    public List<Ticket> getTicketsByAirlineCompany(String airlineCompany) {
        return ticketRepository.findTicketsByAirlineCompany(airlineCompany)
                .stream().filter(ticket -> ticket.getDateAndTimeOfDeparture()
                        .isAfter(LocalDateTime.now())).collect(Collectors.toList());
    }

    public List<Ticket> getTicketsByDestination(String destination) {
        return ticketRepository.findTicketsByDestination(destination)
                .stream().filter(ticket -> ticket.getDateAndTimeOfDeparture()
                        .isAfter(LocalDateTime.now())).collect(Collectors.toList());
    }

    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket alterTicket(Integer id, Ticket alteredTicket) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ticket isnt found")
        );

        ticket.setAirlineCompany(alteredTicket.getAirlineCompany());
        ticket.setAirport(alteredTicket.getAirport());
        ticket.setDestination(alteredTicket.getDestination());
        ticket.setDateAndTimeOfDeparture(alteredTicket.getDateAndTimeOfDeparture());
        ticket.setTicketPrice(alteredTicket.getTicketPrice());
        ticket.setTicketsCount(alteredTicket.getTicketsCount(), ticket);
        ticket.setTicketsSold(alteredTicket.getTicketsSold());
        ticket.setIsSeatTaken(alteredTicket.getIsSeatTaken());

        ticketRepository.save(ticket);

        return ticket;
    }

    public void deleteTicketById(Integer id) {
        ticketRepository.deleteById(id);
    }
}
