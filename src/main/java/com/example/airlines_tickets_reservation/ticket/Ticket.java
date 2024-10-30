package com.example.airlines_tickets_reservation.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String airlineCompany;

    @Column(nullable = false)
    private String airport;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateAndTimeOfDeparture;

    @Column(nullable = false)
    private double ticketPrice;

    @Column(nullable = false)
    private int ticketsCount;

    @Column
    private int ticketsSold;

    //schema with the booked seats
    @Column(columnDefinition = "json")
    @Convert(converter = BooleanArrayToStringConverter.class)
    boolean[] isSeatTaken;

    public Ticket(Integer id, String airlineCompany, String airport,
                  String destination, LocalDateTime dateAndTimeOfDeparture,
                  double ticketPrice, int ticketsCount) {
        this.id = id;
        this.airlineCompany = airlineCompany;
        this.airport = airport;
        this.destination = destination;
        this.dateAndTimeOfDeparture = dateAndTimeOfDeparture;
        this.ticketPrice = ticketPrice;
        this.ticketsCount = ticketsCount;
        this.isSeatTaken = isSeatTaken != null ? isSeatTaken : new boolean[ticketsCount];
    }

    public void setAirlineCompany(String airlineCompany) {
        if(airlineCompany != null){
            this.airlineCompany = airlineCompany;
        }
    }

    public void setAirport(String airport) {
        if(airport != null){
            this.airport = airport;
        }
    }

    public void setDestination(String destination) {
        if(destination != null){
            this.destination = destination;
        }
    }

    public void setDateAndTimeOfDeparture(LocalDateTime dateAndTimeOfDeparture) {
        if(dateAndTimeOfDeparture != null){
            this.dateAndTimeOfDeparture = dateAndTimeOfDeparture;
        }
    }

    public void setTicketPrice(double ticketPrice) {
        if(ticketPrice != 0) {
            this.ticketPrice = ticketPrice;
        }
    }

    public void setTicketsCount(int ticketsCount,Ticket ticket) {
        if(ticketsCount != 0){
            this.ticketsCount = ticketsCount;
        }

        ticket.updateIsSeatTakenByNewSeatsCount();
    }

    public void setTicketsSold(int ticketsSold){
        if(ticketsSold != 0){
            this.ticketsSold = ticketsSold;
        }
    }

    public void setIsSeatTaken(boolean[] isSeatTaken){
        if(isSeatTaken != null){
            this.isSeatTaken = isSeatTaken;
        }
    }

    @PrePersist
    private void initializeIsSeatTaken(){
        if(this.isSeatTaken == null){
            this.isSeatTaken = new boolean[ticketsCount];
        }
    }

    private void updateIsSeatTakenByNewSeatsCount(){
        List<Integer> takenSeatsIndex = new ArrayList<>();

        for (int i = 0; i < isSeatTaken.length; i++) {
            if(isSeatTaken[i]){
                takenSeatsIndex.add(i);
            }
        }

        isSeatTaken = new boolean[ticketsCount];

        for (int index : takenSeatsIndex){
            if(index < isSeatTaken.length){
                isSeatTaken[index] = true;
            }
        }
    }

    public int generateRandomSeat(){
        for (int i = 0; i < isSeatTaken.length; i++) {
            if(!isSeatTaken[i]){
                return i;
            }
        }

        //by normal occasion code should never reach here
        return -1;
    }
}
