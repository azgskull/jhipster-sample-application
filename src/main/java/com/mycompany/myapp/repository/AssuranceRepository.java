package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Assurance;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Assurance entity.
 */
@Repository
public interface AssuranceRepository extends JpaRepository<Assurance, Long> {
    @Query(
        value = "select distinct assurance from Assurance assurance left join fetch assurance.sportifs",
        countQuery = "select count(distinct assurance) from Assurance assurance"
    )
    Page<Assurance> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct assurance from Assurance assurance left join fetch assurance.sportifs")
    List<Assurance> findAllWithEagerRelationships();

    @Query("select assurance from Assurance assurance left join fetch assurance.sportifs where assurance.id =:id")
    Optional<Assurance> findOneWithEagerRelationships(@Param("id") Long id);
}
