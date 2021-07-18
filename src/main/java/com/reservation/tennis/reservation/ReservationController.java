package com.reservation.tennis.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for mapping REST endpoints connected to reservation.
 */
@RestController
@RequestMapping(path = "api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * HTTP GET method
     * @return JSON data of reservations stored in database.
     */
    @GetMapping()
    public List<Reservation> getReservations(){
        return reservationService.getReservations();
    }

    /**
     * HTTP GET method
     * @return JSON data of reservations for given court id.
     */
    @GetMapping(path = "find/court_id/{courtId}")
    public List<Reservation> getReservationById(@PathVariable("courtId") Long courtId){
        return reservationService.getReservationsById(courtId);
    }

    /**
     * HTTP GET method
     * @return JSON list of reservation for given telephone number.
     */
    @GetMapping(path = "find/telephone_number/{telephoneNumber}")
    public List<Reservation> getReservationByTelephone(@PathVariable("telephoneNumber") String telephoneNumber){
        return reservationService.getReservationsByTelephoneNumber(telephoneNumber);
    }

    /**
     * HTTP POST method
     * @param courtId id of court on which will be new reservation
     * @param reservation data of reservation, gotten from body of request
     * @return price of the new reservation.
     */
    @PostMapping(path = "{courtId}/new", consumes={"application/json"})
    public String makeNewReservation(
            @PathVariable("courtId") Long courtId,
            @RequestBody Reservation reservation
    )
    {
        return reservationService.makeNewReservation(courtId, reservation);
    }

    /**
     * HTTP DELETE method
     * @param reservationId reservation to be deleted
     */
    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId){
        reservationService.deleteReservation(reservationId);
    }

}
