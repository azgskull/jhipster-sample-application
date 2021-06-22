package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Structure;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Structure entity.
 */
@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {
    @Query(
        value = "select distinct structure from Structure structure left join fetch structure.disciplines",
        countQuery = "select count(distinct structure) from Structure structure"
    )
    Page<Structure> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct structure from Structure structure left join fetch structure.disciplines")
    List<Structure> findAllWithEagerRelationships();

    @Query("select structure from Structure structure left join fetch structure.disciplines where structure.id =:id")
    Optional<Structure> findOneWithEagerRelationships(@Param("id") Long id);
}
