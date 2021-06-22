package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TypeCertificatDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TypeCertificat}.
 */
public interface TypeCertificatService {
    /**
     * Save a typeCertificat.
     *
     * @param typeCertificatDTO the entity to save.
     * @return the persisted entity.
     */
    TypeCertificatDTO save(TypeCertificatDTO typeCertificatDTO);

    /**
     * Partially updates a typeCertificat.
     *
     * @param typeCertificatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeCertificatDTO> partialUpdate(TypeCertificatDTO typeCertificatDTO);

    /**
     * Get all the typeCertificats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeCertificatDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeCertificat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeCertificatDTO> findOne(Long id);

    /**
     * Delete the "id" typeCertificat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
