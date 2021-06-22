package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OrganismeAssurance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrganismeAssurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganismeAssuranceRepository extends JpaRepository<OrganismeAssurance, Long> {}
