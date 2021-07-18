package com.reservation.tennis.surface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsible for accesing data in Surface table.
 */
@Repository
public interface SurfaceRepository  extends JpaRepository<Surface, Long> {
}
