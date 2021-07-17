package com.reservation.tennis.surface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurfaceRepository  extends JpaRepository<Surface, Long> {
}
