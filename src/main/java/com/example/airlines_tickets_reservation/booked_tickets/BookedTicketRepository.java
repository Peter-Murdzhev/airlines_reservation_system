package com.example.airlines_tickets_reservation.booked_tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedTicketRepository extends JpaRepository<BookedTicket,Integer> {
//    List<BookedTicket> findByTicketId(Integer ticketId);

    @Query(value = "SELECT user_id from booked_ticket WHERE ticket_id=? AND seat_index=? LIMIT 1",
    nativeQuery = true)
    Integer getUserIdFromTicketIdAndSeatNum(Integer ticketId, int seatIndex);

    List<BookedTicket> findByUserId(Integer userId);
}
