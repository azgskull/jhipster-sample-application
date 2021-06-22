package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SeanceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Seance}.
 */
public interface SeanceService {
    /**
     * Save a seance.
     *
     * @param seanceDTO the entity to save.
     * @return the persisted entity.
     */
    SeanceDTO save(SeanceDTO seanceDTO);

    /**
     * Partially updates a seance.
     *
     * @param seanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeanceDTO> partialUpdate(SeanceDTO seanceDTO);

    /**
     * Get all the seances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SeanceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" seance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeanceDTO> findOne(Long id);

    /**
     * Delete the "id" seance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
