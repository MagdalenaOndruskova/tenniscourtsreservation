package com.reservation.tennis.reservation;

import com.reservation.tennis.court.Court;
import com.reservation.tennis.court.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CourtRepository courtRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, CourtRepository courtRepository) {
        this.reservationRepository = reservationRepository;
        this.courtRepository = courtRepository;
    }


    public List<Reservation> getReservations(){
        return reservationRepository.findAll();
    }

    public void makeNewReservation(Long courtId, Reservation reservation) {
        System.out.println("v service ");
        System.out.println(courtId);
        System.out.println(courtRepository.findById(courtId)); //todo ci kurt existuje
        System.out.println(reservation);
        Optional<Court> court = courtRepository.findById(courtId);
        if (court.isPresent())
        {
            Court courtReservation = court.get();
            System.out.println(courtReservation);
            reservation.setCourt(courtReservation);
        }
        System.out.println(reservation);
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

        // checks if duration of reservation is bigger than 0 - if time given (from, to) was legit
        if (reservation.getDuration() < 0 ){
            throw new IllegalStateException("Cant start reservation after its supposed end.");
        }

//        System.out.println(courtRepository.findById(courtId));
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

    public List<Reservation> getReservationsById(Long courtId) {
        Court court =  courtRepository.findById(courtId).
                orElseThrow(()-> new IllegalStateException("Court with id " + courtId  + " doesnt exists."));
        return  reservationRepository.findReservationsByCourt(court);
    }

    public List<Reservation> getReservationsByTelephoneNumber(String telephoneNumber) {
        List <Reservation> reservations = reservationRepository.findReservationsByTelephoneNumber(telephoneNumber);
        if (reservations.isEmpty()) {
            throw new IllegalStateException("No reservations found with given telephone number: " + telephoneNumber);
        }

        return reservations;
    }
}
