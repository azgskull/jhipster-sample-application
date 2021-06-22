package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CertificatRepository;
import com.mycompany.myapp.service.CertificatService;
import com.mycompany.myapp.service.dto.CertificatDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Certificat}.
 */
@RestController
@RequestMapping("/api")
public class CertificatResource {

    private final Logger log = LoggerFactory.getLogger(CertificatResource.class);

    private static final String ENTITY_NAME = "certificat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertificatService certificatService;

    private final CertificatRepository certificatRepository;

    public CertificatResource(CertificatService certificatService, CertificatRepository certificatRepository) {
        this.certificatService = certificatService;
        this.certificatRepository = certificatRepository;
    }

    /**
     * {@code POST  /certificats} : Create a new certificat.
     *
     * @param certificatDTO the certificatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certificatDTO, or with status {@code 400 (Bad Request)} if the certificat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/certificats")
    public ResponseEntity<CertificatDTO> createCertificat(@Valid @RequestBody CertificatDTO certificatDTO) throws URISyntaxException {
        log.debug("REST request to save Certificat : {}", certificatDTO);
        if (certificatDTO.getId() != null) {
            throw new BadRequestAlertException("A new certificat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CertificatDTO result = certificatService.save(certificatDTO);
        return ResponseEntity
            .created(new URI("/api/certificats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /certificats/:id} : Updates an existing certificat.
     *
     * @param id the id of the certificatDTO to save.
     * @param certificatDTO the certificatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificatDTO,
     * or with status {@code 400 (Bad Request)} if the certificatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certificatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/certificats/{id}")
    public ResponseEntity<CertificatDTO> updateCertificat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CertificatDTO certificatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Certificat : {}, {}", id, certificatDTO);
        if (certificatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CertificatDTO result = certificatService.save(certificatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /certificats/:id} : Partial updates given fields of an existing certificat, field will ignore if it is null
     *
     * @param id the id of the certificatDTO to save.
     * @param certificatDTO the certificatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certificatDTO,
     * or with status {@code 400 (Bad Request)} if the certificatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the certificatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the certificatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/certificats/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CertificatDTO> partialUpdateCertificat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CertificatDTO certificatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Certificat partially : {}, {}", id, certificatDTO);
        if (certificatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, certificatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!certificatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CertificatDTO> result = certificatService.partialUpdate(certificatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, certificatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /certificats} : get all the certificats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certificats in body.
     */
    @GetMapping("/certificats")
    public ResponseEntity<List<CertificatDTO>> getAllCertificats(Pageable pageable) {
        log.debug("REST request to get a page of Certificats");
        Page<CertificatDTO> page = certificatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /certificats/:id} : get the "id" certificat.
     *
     * @param id the id of the certificatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certificatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/certificats/{id}")
    public ResponseEntity<CertificatDTO> getCertificat(@PathVariable Long id) {
        log.debug("REST request to get Certificat : {}", id);
        Optional<CertificatDTO> certificatDTO = certificatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certificatDTO);
    }

    /**
     * {@code DELETE  /certificats/:id} : delete the "id" certificat.
     *
     * @param id the id of the certificatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/certificats/{id}")
    public ResponseEntity<Void> deleteCertificat(@PathVariable Long id) {
        log.debug("REST request to delete Certificat : {}", id);
        certificatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
