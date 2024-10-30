package com.example.airlines_tickets_reservation.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TicketDTO(Integer id, String airlineCompany, String airport, String destination,
                        @JsonFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime dateAndTimeOfDeparture, double ticketPrice) {
}
