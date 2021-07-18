package com.reservation.tennis.reservation;

import com.reservation.tennis.court.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface responsible for accesing data in Reservation table.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long > {

    List<Reservation> findReservationsByTelephoneNumber(String telephoneNumber);
    List<Reservation> findReservationsByCourt(Court court);

}

