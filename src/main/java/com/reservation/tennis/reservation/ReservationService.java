package com.reservation.tennis.reservation;

import com.reservation.tennis.court.Court;
import com.reservation.tennis.court.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service for handling reservation business logic
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CourtRepository courtRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, CourtRepository courtRepository) {
        this.reservationRepository = reservationRepository;
        this.courtRepository = courtRepository;
    }

    /**
     * Function finds all reservation stored in database.
     * @return list of reservation objects
     */
    public List<Reservation> getReservations(){
        return reservationRepository.findAll();
    }

    /**
     * Function handles creating new reservation.
     * Function checks for:
     *         -- telephone number if its stored with given name or with another name (if with another name, it returns error)
     *         -- court availability
     * @param courtId court
     * @param reservation new reservation
     * @return
     */
    public String makeNewReservation(Long courtId, Reservation reservation) {
        Optional<Court> court = courtRepository.findById(courtId);
        if (court.isPresent())
        {
            Court courtReservation = court.get();
            courtReservation.checkCourtAvailability(reservation.getDate(), reservation.getFrom(), reservation.getTo());
            reservation.setCourt(courtReservation);
        }
        else {
            throw new IllegalStateException("Wrong ID of court given. Court doesnt exist."); // court doesnt exist
        }
        List<Reservation> reservationsByTelephone = reservationRepository.findReservationsByTelephoneNumber(reservation.getTelephoneNumber());

        // checks if telephoneNumber is not used with different name
        if (!reservationsByTelephone.isEmpty()){
            for (Reservation res: reservationsByTelephone
                 ) {
                if (!Objects.equals(res.getName(),reservation.getName())){
                    throw new IllegalStateException("With given telephone number is used different name!");
                }
            }
        }
        reservation.setDuration(reservation.countDuration());

        // checks if duration of reservation is bigger than 0 - if time given (from, to) was legit
        if (reservation.getDuration() < 0 ){
            throw new IllegalStateException("Cant start reservation after its supposed end.");
        }

        double countedPrice = reservation.countPrice();
        reservation.setPrice(countedPrice);
        reservationRepository.save(reservation);

        return String.format("The price of reservation will be: %.2f CZK.", countedPrice);

    }

    /**
     * Function handles deleting reservation. Checks, if reservation with given id exists.
     * @param reservationId reservation to be deleted
     */
    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)){
            throw new IllegalStateException("Reservation with id " + reservationId  + " doesnt exists.");
        }
        reservationRepository.deleteById(reservationId);
    }

    /**
     * Function finds all reservation with given court id
     * @param courtId court id
     * @return list of reservation objects with given court id
     */
    public List<Reservation> getReservationsById(Long courtId) {
        Court court =  courtRepository.findById(courtId).
                orElseThrow(()-> new IllegalStateException("Court with id " + courtId  + " doesnt exists."));
        return  reservationRepository.findReservationsByCourt(court);
    }

    /**
     * Function finds all reservation with given telephone number
     * @param telephoneNumber given telephone number
     * @return list of reservations
     */
    public List<Reservation> getReservationsByTelephoneNumber(String telephoneNumber) {
        List <Reservation> reservations = reservationRepository.findReservationsByTelephoneNumber(telephoneNumber);
        if (reservations.isEmpty()) {
            throw new IllegalStateException("No reservations found with given telephone number: " + telephoneNumber);
        }

        return reservations;
    }


}
