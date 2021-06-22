package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ModePaiementDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ModePaiement}.
 */
public interface ModePaiementService {
    /**
     * Save a modePaiement.
     *
     * @param modePaiementDTO the entity to save.
     * @return the persisted entity.
     */
    ModePaiementDTO save(ModePaiementDTO modePaiementDTO);

    /**
     * Partially updates a modePaiement.
     *
     * @param modePaiementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModePaiementDTO> partialUpdate(ModePaiementDTO modePaiementDTO);

    /**
     * Get all the modePaiements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModePaiementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" modePaiement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModePaiementDTO> findOne(Long id);

    /**
     * Delete the "id" modePaiement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
