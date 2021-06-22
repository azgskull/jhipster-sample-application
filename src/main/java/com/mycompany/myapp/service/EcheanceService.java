package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EcheanceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Echeance}.
 */
public interface EcheanceService {
    /**
     * Save a echeance.
     *
     * @param echeanceDTO the entity to save.
     * @return the persisted entity.
     */
    EcheanceDTO save(EcheanceDTO echeanceDTO);

    /**
     * Partially updates a echeance.
     *
     * @param echeanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EcheanceDTO> partialUpdate(EcheanceDTO echeanceDTO);

    /**
     * Get all the echeances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EcheanceDTO> findAll(Pageable pageable);

    /**
     * Get all the echeances with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EcheanceDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" echeance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EcheanceDTO> findOne(Long id);

    /**
     * Delete the "id" echeance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
