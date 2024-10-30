package com.example.airlines_tickets_reservation.booked_tickets;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class BookedTicket{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(
            nullable = false
    )
    int userId;

    @Column(
            nullable = false
    )
    int ticketId;

    @Column
    int seatIndex;
}
