package com.betcoin.repository;

import com.betcoin.domain.Pronotype;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pronotype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PronotypeRepository extends JpaRepository<Pronotype,Long> {
    
}
