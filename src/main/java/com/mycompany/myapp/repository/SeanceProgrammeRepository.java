package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SeanceProgramme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SeanceProgramme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeanceProgrammeRepository extends JpaRepository<SeanceProgramme, Long> {}
