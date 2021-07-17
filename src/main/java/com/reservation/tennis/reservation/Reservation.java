package com.reservation.tennis.reservation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reservation.tennis.court.Court;
import com.reservation.tennis.surface.Surface;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.MINUTES;


@Entity
@Table
public class Reservation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    private LocalDate date;
    private LocalTime from;
    private LocalTime to;

    @Transient
    private Long duration;

    private Boolean typeSingle; // true - Single, false - Doubles
    private Integer price;

    private String telephoneNumber;  // todo - mozno iny format?
    private String name;

    @ManyToOne
    @JoinColumn(name="court_id", nullable = false)
    @JsonBackReference
    private Court court;

    public Reservation() {
    }

    public Reservation(Long id,
                       LocalDate date,
                       LocalTime from,
                       LocalTime to,
                       Long duration,
                       Boolean typeSingle,
                       Integer price,
                       String telephoneNumber,
                       String name,
                       Court court) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.typeSingle = typeSingle;
        this.price = price;
        this.telephoneNumber = telephoneNumber;
        this.name = name;
        this.court = court;
    }
    public Reservation(LocalDate date,
                       LocalTime from,
                       LocalTime to,
                       Long duration,
                       Boolean typeSingle,
                       Integer price,
                       String telephoneNumber,
                       String name,
                       Court court) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.typeSingle = typeSingle;
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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
        Long diff = MINUTES.between(from, to);
        return diff;
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

//    @Override
//    public String toString() {
//        return "Reservation{" +
//                "id=" + id +
//                ", date=" + date +
//                ", from=" + from +
//                ", to=" + to +
//                ", duration=" + duration +
//                ", typeSingle=" + typeSingle +
//                ", price=" + price +
//                ", telephoneNumber='" + telephoneNumber + '\'' +
//                ", name='" + name + '\'' +
//                '}';
//    }


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
