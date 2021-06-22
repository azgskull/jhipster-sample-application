package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TypeSeanceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TypeSeance}.
 */
public interface TypeSeanceService {
    /**
     * Save a typeSeance.
     *
     * @param typeSeanceDTO the entity to save.
     * @return the persisted entity.
     */
    TypeSeanceDTO save(TypeSeanceDTO typeSeanceDTO);

    /**
     * Partially updates a typeSeance.
     *
     * @param typeSeanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeSeanceDTO> partialUpdate(TypeSeanceDTO typeSeanceDTO);

    /**
     * Get all the typeSeances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeSeanceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeSeance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeSeanceDTO> findOne(Long id);

    /**
     * Delete the "id" typeSeance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
