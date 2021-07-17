package com.reservation.tennis.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired // reservationService je magically auto wired do premennej ktoru berie tato funkce
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public List<Reservation> getReservations(){
        return reservationService.getReservations();
    }

    @GetMapping(path = "find/id/{reservationId}")
    public Reservation getReservationById(@PathVariable("reservationId") Long reservationId){
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping(path = "find/telephone_number/{telephoneNumber}")
    public List<Reservation> getReservationByTelephone(@PathVariable("telephoneNumber") String telephoneNumber){
        return reservationService.getReservationsByTelephoneNumber(telephoneNumber);
    }

    @PostMapping(path = "new")
    public void makeNewReservation(@RequestBody Reservation reservation){
        reservationService.makeNewReservation(reservation);
    }

    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId){
        reservationService.deleteReservation(reservationId);
    }

    @PutMapping(path = "{reservationId}")
    public void updateReservation(
            @PathVariable("reservationId") Long reservationId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String telephoneNumber
    ){
        reservationService.updateReservation(reservationId, name, telephoneNumber);
    }
}
