package com.example.airlines_tickets_reservation.booked_tickets;

public class TakenSeatException extends RuntimeException{
    public TakenSeatException(String message){
        super(message);
    }
}
