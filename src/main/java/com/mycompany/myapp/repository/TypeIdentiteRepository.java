package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TypeIdentite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeIdentite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeIdentiteRepository extends JpaRepository<TypeIdentite, Long> {}
