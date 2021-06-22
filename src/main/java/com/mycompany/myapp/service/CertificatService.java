package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CertificatDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Certificat}.
 */
public interface CertificatService {
    /**
     * Save a certificat.
     *
     * @param certificatDTO the entity to save.
     * @return the persisted entity.
     */
    CertificatDTO save(CertificatDTO certificatDTO);

    /**
     * Partially updates a certificat.
     *
     * @param certificatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CertificatDTO> partialUpdate(CertificatDTO certificatDTO);

    /**
     * Get all the certificats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CertificatDTO> findAll(Pageable pageable);

    /**
     * Get the "id" certificat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CertificatDTO> findOne(Long id);

    /**
     * Delete the "id" certificat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
