package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PaiementStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PaiementStatus}.
 */
public interface PaiementStatusService {
    /**
     * Save a paiementStatus.
     *
     * @param paiementStatusDTO the entity to save.
     * @return the persisted entity.
     */
    PaiementStatusDTO save(PaiementStatusDTO paiementStatusDTO);

    /**
     * Partially updates a paiementStatus.
     *
     * @param paiementStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaiementStatusDTO> partialUpdate(PaiementStatusDTO paiementStatusDTO);

    /**
     * Get all the paiementStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaiementStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paiementStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaiementStatusDTO> findOne(Long id);

    /**
     * Delete the "id" paiementStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
