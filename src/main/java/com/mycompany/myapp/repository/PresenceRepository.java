package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Presence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Presence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {}
