package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Echeance;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Echeance entity.
 */
@Repository
public interface EcheanceRepository extends JpaRepository<Echeance, Long> {
    @Query(
        value = "select distinct echeance from Echeance echeance left join fetch echeance.seances",
        countQuery = "select count(distinct echeance) from Echeance echeance"
    )
    Page<Echeance> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct echeance from Echeance echeance left join fetch echeance.seances")
    List<Echeance> findAllWithEagerRelationships();

    @Query("select echeance from Echeance echeance left join fetch echeance.seances where echeance.id =:id")
    Optional<Echeance> findOneWithEagerRelationships(@Param("id") Long id);
}
