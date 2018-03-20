package com.betcoin.repository;

import com.betcoin.domain.Gamer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Gamer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamerRepository extends JpaRepository<Gamer,Long> {
    
}
