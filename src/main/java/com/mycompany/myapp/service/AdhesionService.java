package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AdhesionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Adhesion}.
 */
public interface AdhesionService {
    /**
     * Save a adhesion.
     *
     * @param adhesionDTO the entity to save.
     * @return the persisted entity.
     */
    AdhesionDTO save(AdhesionDTO adhesionDTO);

    /**
     * Partially updates a adhesion.
     *
     * @param adhesionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdhesionDTO> partialUpdate(AdhesionDTO adhesionDTO);

    /**
     * Get all the adhesions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdhesionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adhesion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdhesionDTO> findOne(Long id);

    /**
     * Delete the "id" adhesion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
