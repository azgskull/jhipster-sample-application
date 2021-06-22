package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Seance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Seance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {}
