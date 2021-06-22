package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DisciplineDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Discipline}.
 */
public interface DisciplineService {
    /**
     * Save a discipline.
     *
     * @param disciplineDTO the entity to save.
     * @return the persisted entity.
     */
    DisciplineDTO save(DisciplineDTO disciplineDTO);

    /**
     * Partially updates a discipline.
     *
     * @param disciplineDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DisciplineDTO> partialUpdate(DisciplineDTO disciplineDTO);

    /**
     * Get all the disciplines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisciplineDTO> findAll(Pageable pageable);

    /**
     * Get the "id" discipline.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisciplineDTO> findOne(Long id);

    /**
     * Delete the "id" discipline.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
