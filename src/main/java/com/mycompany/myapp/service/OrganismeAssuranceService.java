package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.OrganismeAssuranceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.OrganismeAssurance}.
 */
public interface OrganismeAssuranceService {
    /**
     * Save a organismeAssurance.
     *
     * @param organismeAssuranceDTO the entity to save.
     * @return the persisted entity.
     */
    OrganismeAssuranceDTO save(OrganismeAssuranceDTO organismeAssuranceDTO);

    /**
     * Partially updates a organismeAssurance.
     *
     * @param organismeAssuranceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganismeAssuranceDTO> partialUpdate(OrganismeAssuranceDTO organismeAssuranceDTO);

    /**
     * Get all the organismeAssurances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganismeAssuranceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" organismeAssurance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganismeAssuranceDTO> findOne(Long id);

    /**
     * Delete the "id" organismeAssurance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
