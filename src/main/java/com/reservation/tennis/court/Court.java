package com.reservation.tennis.court;

import com.fasterxml.jackson.annotation.*;
import com.reservation.tennis.reservation.Reservation;
import com.reservation.tennis.surface.Surface;

import javax.persistence.*;
import java.util.Set;

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
