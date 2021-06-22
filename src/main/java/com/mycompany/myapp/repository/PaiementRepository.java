package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Paiement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Paiement entity.
 */
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    @Query(
        value = "select distinct paiement from Paiement paiement left join fetch paiement.echeances",
        countQuery = "select count(distinct paiement) from Paiement paiement"
    )
    Page<Paiement> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct paiement from Paiement paiement left join fetch paiement.echeances")
    List<Paiement> findAllWithEagerRelationships();

    @Query("select paiement from Paiement paiement left join fetch paiement.echeances where paiement.id =:id")
    Optional<Paiement> findOneWithEagerRelationships(@Param("id") Long id);
}
