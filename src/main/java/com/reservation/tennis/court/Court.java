package com.reservation.tennis.court;

import com.fasterxml.jackson.annotation.*;
import com.reservation.tennis.reservation.Reservation;
import com.reservation.tennis.surface.Surface;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * Class for handling table court, with its construct methods, toString method,
 * getters and setters and other functions related to court logic.
 */
@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="surface_id", nullable = false)
    @JsonManagedReference
    private Surface surface;

    @OneToMany(mappedBy = "court")
    @JsonBackReference
    @JsonIgnore
    private Set<Reservation> reservations;

    public Court() {
    }

    public Court(Long id, String name, Surface surface) {
        this.id = id;
        this.name = name;
        this.surface = surface;
    }

    public Court(String name, Surface surface) {
        this.name = name;
        this.surface = surface;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    /**
     * Function checks availability of court - if there isnt already some reservation on
     * given date and time, or if the new reservation can run whole.
     * @param date date of new reservation
     * @param from start of new reservation
     * @param to ened of new reservation
     * @return true if reservation can run whole, else it throws exception
     */
    public boolean checkCourtAvailability(LocalDate date, LocalTime from, LocalTime to){
        for (Reservation res: reservations
             ) {

            if (Objects.equals(res.getDate(), date)) // checks if some reservation exists on day of new reservation
            {
                if (res.getFrom().compareTo(from) == 0) { // reservations starts at the same time
                    throw new IllegalStateException("There already is reservation, which starts at the same time. Couldnt make the reservation.");
                }
                else if (res.getFrom().compareTo(from) < 0 ) { // from (new reservation) is later than start of existing reservation
                    if (res.getTo().compareTo(from) > 0) { //existing reservation ends later than start of new reservation
                        throw new IllegalStateException("There already is reservation, which starts sooner than new reservation and it isnt already finished.");
                    }
                }
                else if (res.getFrom().compareTo(from) > 0 ) { // from (new reservation) is sooner than start of existing reservation
                    if (res.getFrom().compareTo(to) < 0) { //existing reservation ends later than start of new reservation
                        throw new IllegalStateException("There already is reservation, which starts later than new reservation so the new reservation cant run whole.");
                    }
                }
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return "Court{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surface=" + surface.getType() +
//                ", reservations=" + reservations +
                '}';
    }
}
