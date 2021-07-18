package com.reservation.tennis.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reservation.tennis.court.Court;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;


/**
 * Class for handling table reservation, with its construct methods, toString method,
 * getters and setters and other functions related to reservation logic.
 */
@Entity
@Table
public class Reservation {

    @Transient
    private final Double DOUBLE_PLAY_CONSTANT = 1.5;

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @NotNull(message = "error.date.notnull")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "error.from.notnull")
    @Column(nullable = false)
    private LocalTime from;

    @NotNull(message = "error.to.notnull")
    @Column(nullable = false)
    private LocalTime to;

    @Transient
    private Long duration = 0L;

    private Boolean typeSingle = true; // true - Single, false - Doubles

    private Double price = 0.0;

    @NotNull(message = "error.telephoneNumber.notnull")
    @Column(nullable = false)
    private String telephoneNumber;  // todo - regex validation

    @NotNull(message = "error.name.notnull")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="court_id", nullable = false)
//    @JsonBackReference
    @JsonIgnore
    private Court court;

    public Reservation() {
    }

    public Reservation(Long id,
                       @NotNull(message = "error.date.notnull") LocalDate date,
                       @NotNull(message = "error.from.notnull") LocalTime from,
                       @NotNull(message = "error.to.notnull") LocalTime to,
                       Boolean typeSingle,
                       @NotNull(message = "error.telephoneNumber.notnull") String telephoneNumber,
                       @NotNull(message = "error.name.notnull") String name,
                       Court court) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.typeSingle = typeSingle;
        this.telephoneNumber = telephoneNumber;
        this.name = name;
        this.court = court;
    }

    public Reservation(
            @NotNull(message = "error.date.notnull") LocalDate date,
            @NotNull(message = "error.from.notnull") LocalTime from,
            @NotNull(message = "error.to.notnull") LocalTime to,
            Boolean typeSingle,
            @NotNull(message = "error.telephoneNumber.notnull") String telephoneNumber,
            @NotNull(message = "error.name.notnull") String name,
            Court court) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.typeSingle = typeSingle;
        this.telephoneNumber = telephoneNumber;
        this.name = name;
        this.court = court;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Boolean getTypeSingle() {
        return typeSingle;
    }

    public void setTypeSingle(Boolean typeSingle) {
        this.typeSingle = typeSingle;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        double recountedPrice = this.countPrice();
        if (price != recountedPrice){
            price = recountedPrice;
        }
        this.price = price;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    /**
     * Function counts price of the reservation
     * @return final price of reservation
     */
    public double countPrice(){
        long duration = this.getDuration();
        double countedPrice = duration*this.court.getSurface().getCost();
        if (! typeSingle){ // doubles
            countedPrice = countedPrice*DOUBLE_PLAY_CONSTANT;
        }
        return countedPrice;
    }

    /**
     * Function counts length (duration) of reservation. Reservation has to be at leas 15min long,
     * and cant end on time not divisable by 5.
     * @return duration of reservation
     */
    public long countDuration(){
        Long diff = MINUTES.between(from, to);
        if (diff < 15) {
            throw new IllegalStateException("Duration of game has to be atleast 15minutes. ");
        }
        Long modulo = diff % 5;
        if (modulo > 0){
            throw new IllegalStateException("Duration of game has to be at least 15/20/25...minutes (divisable by 5).");
        }
        return diff;
    }

    @Override
    public String toString() {
        if (court != null) {
            return "Reservation{" +
                    "id=" + id +
                    ", date=" + date +
                    ", from=" + from +
                    ", to=" + to +
                    ", duration=" + duration +
                    ", typeSingle=" + typeSingle +
                    ", price=" + price +
                    ", telephoneNumber='" + telephoneNumber + '\'' +
                    ", name='" + name + '\'' +
                    ", court=" + court.getName() +
                    '}';
        }
        else {
            return "Reservation{" +
                    "id=" + id +
                    ", date=" + date +
                    ", from=" + from +
                    ", to=" + to +
                    ", duration=" + duration +
                    ", typeSingle=" + typeSingle +
                    ", price=" + price +
                    ", telephoneNumber='" + telephoneNumber + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
