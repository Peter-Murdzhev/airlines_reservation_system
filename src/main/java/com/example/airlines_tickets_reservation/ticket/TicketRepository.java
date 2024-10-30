package com.example.airlines_tickets_reservation.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findTicketsByDestination(String destination);

    List<Ticket> findTicketsByAirlineCompany(String airlineCOmpany);
}
