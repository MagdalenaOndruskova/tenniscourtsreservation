package com.reservation.tennis.court;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for mapping REST endpoints connected to court.
 */
@RestController
@RequestMapping(path = "api/v1/court")
public class CourtController {
    private final CourtService courtService;

    @Autowired
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    /**
     * HTTP GET method
     * @return JSON data of courts stored in database.
     */
    @GetMapping
    public List<Court> getCourts(){
        List<Court> courts = courtService.getCourts();

        return courts;
    }
}
