package com.betcoin.repository;

import com.betcoin.domain.Pronostic;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pronostic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PronosticRepository extends JpaRepository<Pronostic,Long> {
    
}
