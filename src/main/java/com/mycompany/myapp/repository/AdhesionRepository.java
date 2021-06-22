package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Adhesion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Adhesion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdhesionRepository extends JpaRepository<Adhesion, Long> {}
