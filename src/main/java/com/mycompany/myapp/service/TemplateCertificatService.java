package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TemplateCertificatDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TemplateCertificat}.
 */
public interface TemplateCertificatService {
    /**
     * Save a templateCertificat.
     *
     * @param templateCertificatDTO the entity to save.
     * @return the persisted entity.
     */
    TemplateCertificatDTO save(TemplateCertificatDTO templateCertificatDTO);

    /**
     * Partially updates a templateCertificat.
     *
     * @param templateCertificatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TemplateCertificatDTO> partialUpdate(TemplateCertificatDTO templateCertificatDTO);

    /**
     * Get all the templateCertificats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TemplateCertificatDTO> findAll(Pageable pageable);

    /**
     * Get the "id" templateCertificat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TemplateCertificatDTO> findOne(Long id);

    /**
     * Delete the "id" templateCertificat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
