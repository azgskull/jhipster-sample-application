package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PaysDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Pays}.
 */
public interface PaysService {
    /**
     * Save a pays.
     *
     * @param paysDTO the entity to save.
     * @return the persisted entity.
     */
    PaysDTO save(PaysDTO paysDTO);

    /**
     * Partially updates a pays.
     *
     * @param paysDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaysDTO> partialUpdate(PaysDTO paysDTO);

    /**
     * Get all the pays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaysDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pays.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaysDTO> findOne(Long id);

    /**
     * Delete the "id" pays.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
