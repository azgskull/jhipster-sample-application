package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TypeSeance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeSeance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeSeanceRepository extends JpaRepository<TypeSeance, Long> {}
