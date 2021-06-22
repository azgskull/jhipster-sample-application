package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TypeCertificatRepository;
import com.mycompany.myapp.service.TypeCertificatService;
import com.mycompany.myapp.service.dto.TypeCertificatDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TypeCertificat}.
 */
@RestController
@RequestMapping("/api")
public class TypeCertificatResource {

    private final Logger log = LoggerFactory.getLogger(TypeCertificatResource.class);

    private static final String ENTITY_NAME = "typeCertificat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeCertificatService typeCertificatService;

    private final TypeCertificatRepository typeCertificatRepository;

    public TypeCertificatResource(TypeCertificatService typeCertificatService, TypeCertificatRepository typeCertificatRepository) {
        this.typeCertificatService = typeCertificatService;
        this.typeCertificatRepository = typeCertificatRepository;
    }

    /**
     * {@code POST  /type-certificats} : Create a new typeCertificat.
     *
     * @param typeCertificatDTO the typeCertificatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeCertificatDTO, or with status {@code 400 (Bad Request)} if the typeCertificat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-certificats")
    public ResponseEntity<TypeCertificatDTO> createTypeCertificat(@Valid @RequestBody TypeCertificatDTO typeCertificatDTO)
        throws URISyntaxException {
        log.debug("REST request to save TypeCertificat : {}", typeCertificatDTO);
        if (typeCertificatDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeCertificat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCertificatDTO result = typeCertificatService.save(typeCertificatDTO);
        return ResponseEntity
            .created(new URI("/api/type-certificats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-certificats/:id} : Updates an existing typeCertificat.
     *
     * @param id the id of the typeCertificatDTO to save.
     * @param typeCertificatDTO the typeCertificatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeCertificatDTO,
     * or with status {@code 400 (Bad Request)} if the typeCertificatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeCertificatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-certificats/{id}")
    public ResponseEntity<TypeCertificatDTO> updateTypeCertificat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeCertificatDTO typeCertificatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeCertificat : {}, {}", id, typeCertificatDTO);
        if (typeCertificatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeCertificatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeCertificatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeCertificatDTO result = typeCertificatService.save(typeCertificatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeCertificatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-certificats/:id} : Partial updates given fields of an existing typeCertificat, field will ignore if it is null
     *
     * @param id the id of the typeCertificatDTO to save.
     * @param typeCertificatDTO the typeCertificatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeCertificatDTO,
     * or with status {@code 400 (Bad Request)} if the typeCertificatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeCertificatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeCertificatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-certificats/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TypeCertificatDTO> partialUpdateTypeCertificat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeCertificatDTO typeCertificatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeCertificat partially : {}, {}", id, typeCertificatDTO);
        if (typeCertificatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeCertificatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeCertificatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeCertificatDTO> result = typeCertificatService.partialUpdate(typeCertificatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeCertificatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-certificats} : get all the typeCertificats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeCertificats in body.
     */
    @GetMapping("/type-certificats")
    public ResponseEntity<List<TypeCertificatDTO>> getAllTypeCertificats(Pageable pageable) {
        log.debug("REST request to get a page of TypeCertificats");
        Page<TypeCertificatDTO> page = typeCertificatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-certificats/:id} : get the "id" typeCertificat.
     *
     * @param id the id of the typeCertificatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeCertificatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-certificats/{id}")
    public ResponseEntity<TypeCertificatDTO> getTypeCertificat(@PathVariable Long id) {
        log.debug("REST request to get TypeCertificat : {}", id);
        Optional<TypeCertificatDTO> typeCertificatDTO = typeCertificatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCertificatDTO);
    }

    /**
     * {@code DELETE  /type-certificats/:id} : delete the "id" typeCertificat.
     *
     * @param id the id of the typeCertificatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-certificats/{id}")
    public ResponseEntity<Void> deleteTypeCertificat(@PathVariable Long id) {
        log.debug("REST request to delete TypeCertificat : {}", id);
        typeCertificatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
