package com.reservation.tennis.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsible for accesing data in Court table.
 */
@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

}
