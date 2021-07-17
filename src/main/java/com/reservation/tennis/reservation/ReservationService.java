package com.reservation.tennis.reservation;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public List<Reservation> getReservations(){
//        List<Reservation> reservations = new ArrayList<>();
//        reservations.add(new Reservation(
//                1L,
//                LocalDate.of(2021, Month.APRIL, 15),
//                LocalTime.of(15, 0),
//                LocalTime.of(17, 0),
//                Boolean.TRUE,
//                20,
//                "+420777534238",
//                "Maggie"
//        )); reservations.add(new Reservation(
//                2L,
//                LocalDate.of(2021, Month.APRIL, 16),
//                LocalTime.of(15, 0),
//                LocalTime.of(17, 0),
//                Boolean.TRUE,
//                20,
//                "+420777534239",
//                "Patka"
//        ));
//        return reservations;
        return reservationRepository.findAll();
    }

    public void makeNewReservation(Reservation reservation) {
        System.out.println(reservation);
//        Optional<Reservation> reservationTelNumber = reservationRepository.
//                findReservationByTelephoneNumber(reservation.getTelephoneNumber());

//        if (reservationTelNumber.isPresent()){
//            throw new IllegalStateException("telephone number used");
//        }
        reservationRepository.save(reservation);

    }

    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)){
            throw new IllegalStateException("Reservation with id " + reservationId  + " doesnt exists.");
        }
        reservationRepository.deleteById(reservationId);
    }

    @Transactional
    public void updateReservation(Long reservationId, String name, String telephoneNumber) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new IllegalStateException("Reservation with id " + reservationId  + " doesnt exists."));

        // ak name nie je null, ak je name dlhsie ako 0, a ak sucasna rezervacia nema rovnake meno
        if (name != null &&  name.length() > 0 && !Objects.equals(reservation.getName(), name)){
            reservation.setName(name);
        }

        if (telephoneNumber != null &&  telephoneNumber.length() > 0 && !Objects.equals(reservation.getTelephoneNumber(), telephoneNumber)){
            reservation.setTelephoneNumber(telephoneNumber);
        }
        System.out.println(reservation);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).
                orElseThrow(()-> new IllegalStateException("Reservation with id " + reservationId  + " doesnt exists."));
    }

    public List<Reservation> getReservationsByTelephoneNumber(String telephoneNumber) {
        List <Reservation> reservations = reservationRepository.findReservationsByTelephoneNumber(telephoneNumber);
        if (reservations.isEmpty()) {
            throw new IllegalStateException("No reservations found with given telephone number: " + telephoneNumber);
        }

        return reservations;
    }
}
