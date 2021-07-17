package com.reservation.tennis.reservation;

import com.reservation.tennis.court.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long > {

    Optional<Reservation> findReservationByTelephoneNumber(String telephoneNumber); // finds reservation by telephoneNumber
    List<Reservation> findReservationsByTelephoneNumber(String telephoneNumber);
    List<Reservation> findReservationsByCourt(Court court);

}

