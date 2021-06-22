package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SeanceProgrammeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.SeanceProgramme}.
 */
public interface SeanceProgrammeService {
    /**
     * Save a seanceProgramme.
     *
     * @param seanceProgrammeDTO the entity to save.
     * @return the persisted entity.
     */
    SeanceProgrammeDTO save(SeanceProgrammeDTO seanceProgrammeDTO);

    /**
     * Partially updates a seanceProgramme.
     *
     * @param seanceProgrammeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeanceProgrammeDTO> partialUpdate(SeanceProgrammeDTO seanceProgrammeDTO);

    /**
     * Get all the seanceProgrammes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SeanceProgrammeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" seanceProgramme.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeanceProgrammeDTO> findOne(Long id);

    /**
     * Delete the "id" seanceProgramme.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
