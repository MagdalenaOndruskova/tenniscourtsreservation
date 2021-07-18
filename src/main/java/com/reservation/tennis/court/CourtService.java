package com.reservation.tennis.court;

import com.reservation.tennis.surface.SurfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling court business logic
 */
@Service
public class CourtService {

    private final CourtRepository courtRepository;
    private final SurfaceRepository surfaceRepository;

    @Autowired
    public CourtService(CourtRepository courtRepository, SurfaceRepository surfaceRepository) {
        this.courtRepository = courtRepository;
        this.surfaceRepository = surfaceRepository;
    }


    /**
     * Function finds all courts stored in database
     * @return list of courts objects
     */
    public List<Court> getCourts() {
        return courtRepository.findAll();
    }
}
