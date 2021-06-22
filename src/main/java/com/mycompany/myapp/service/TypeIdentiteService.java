package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TypeIdentiteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TypeIdentite}.
 */
public interface TypeIdentiteService {
    /**
     * Save a typeIdentite.
     *
     * @param typeIdentiteDTO the entity to save.
     * @return the persisted entity.
     */
    TypeIdentiteDTO save(TypeIdentiteDTO typeIdentiteDTO);

    /**
     * Partially updates a typeIdentite.
     *
     * @param typeIdentiteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeIdentiteDTO> partialUpdate(TypeIdentiteDTO typeIdentiteDTO);

    /**
     * Get all the typeIdentites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeIdentiteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeIdentite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeIdentiteDTO> findOne(Long id);

    /**
     * Delete the "id" typeIdentite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
