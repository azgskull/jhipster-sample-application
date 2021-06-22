package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SportifDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Sportif}.
 */
public interface SportifService {
    /**
     * Save a sportif.
     *
     * @param sportifDTO the entity to save.
     * @return the persisted entity.
     */
    SportifDTO save(SportifDTO sportifDTO);

    /**
     * Partially updates a sportif.
     *
     * @param sportifDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SportifDTO> partialUpdate(SportifDTO sportifDTO);

    /**
     * Get all the sportifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SportifDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sportif.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SportifDTO> findOne(Long id);

    /**
     * Delete the "id" sportif.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
