package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PaiementStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaiementStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaiementStatusRepository extends JpaRepository<PaiementStatus, Long> {}
