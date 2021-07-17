package com.reservation.tennis.surface;

import com.fasterxml.jackson.annotation.*;
import com.reservation.tennis.court.Court;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Surface {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;
    private Double cost;

    @OneToMany(mappedBy = "surface")
    @JsonBackReference
    private Set<Court> courts;

    public Surface() {
    }

    public Surface(Long id, String type, Double cost, Set<Court> courts) {
        this.id = id;
        this.type = type;
        this.cost = cost;
        this.courts = courts;
    }

    public Surface(String type, Double cost, Set<Court> courts) {
        this.type = type;
        this.cost = cost;
        this.courts = courts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Set<Court> getCourts() {
        return courts;
    }

    public void setCourts(Set<Court> courts) {
        this.courts = courts;
    }

    @Override
    public String toString() {
        return "Surface{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", cost=" + cost +
                '}';
    }
}
