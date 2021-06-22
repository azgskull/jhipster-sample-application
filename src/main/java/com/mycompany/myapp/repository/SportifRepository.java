package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sportif;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sportif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportifRepository extends JpaRepository<Sportif, Long> {}
