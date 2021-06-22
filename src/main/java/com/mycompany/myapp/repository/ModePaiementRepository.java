package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ModePaiement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ModePaiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModePaiementRepository extends JpaRepository<ModePaiement, Long> {}
