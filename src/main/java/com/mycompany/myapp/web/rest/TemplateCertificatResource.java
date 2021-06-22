package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TemplateCertificatRepository;
import com.mycompany.myapp.service.TemplateCertificatService;
import com.mycompany.myapp.service.dto.TemplateCertificatDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TemplateCertificat}.
 */
@RestController
@RequestMapping("/api")
public class TemplateCertificatResource {

    private final Logger log = LoggerFactory.getLogger(TemplateCertificatResource.class);

    private static final String ENTITY_NAME = "templateCertificat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateCertificatService templateCertificatService;

    private final TemplateCertificatRepository templateCertificatRepository;

    public TemplateCertificatResource(
        TemplateCertificatService templateCertificatService,
        TemplateCertificatRepository templateCertificatRepository
    ) {
        this.templateCertificatService = templateCertificatService;
        this.templateCertificatRepository = templateCertificatRepository;
    }

    /**
     * {@code POST  /template-certificats} : Create a new templateCertificat.
     *
     * @param templateCertificatDTO the templateCertificatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateCertificatDTO, or with status {@code 400 (Bad Request)} if the templateCertificat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-certificats")
    public ResponseEntity<TemplateCertificatDTO> createTemplateCertificat(@Valid @RequestBody TemplateCertificatDTO templateCertificatDTO)
        throws URISyntaxException {
        log.debug("REST request to save TemplateCertificat : {}", templateCertificatDTO);
        if (templateCertificatDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateCertificat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateCertificatDTO result = templateCertificatService.save(templateCertificatDTO);
        return ResponseEntity
            .created(new URI("/api/template-certificats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-certificats/:id} : Updates an existing templateCertificat.
     *
     * @param id the id of the templateCertificatDTO to save.
     * @param templateCertificatDTO the templateCertificatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateCertificatDTO,
     * or with status {@code 400 (Bad Request)} if the templateCertificatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateCertificatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-certificats/{id}")
    public ResponseEntity<TemplateCertificatDTO> updateTemplateCertificat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TemplateCertificatDTO templateCertificatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TemplateCertificat : {}, {}", id, templateCertificatDTO);
        if (templateCertificatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateCertificatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateCertificatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemplateCertificatDTO result = templateCertificatService.save(templateCertificatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateCertificatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /template-certificats/:id} : Partial updates given fields of an existing templateCertificat, field will ignore if it is null
     *
     * @param id the id of the templateCertificatDTO to save.
     * @param templateCertificatDTO the templateCertificatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateCertificatDTO,
     * or with status {@code 400 (Bad Request)} if the templateCertificatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateCertificatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateCertificatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/template-certificats/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TemplateCertificatDTO> partialUpdateTemplateCertificat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TemplateCertificatDTO templateCertificatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemplateCertificat partially : {}, {}", id, templateCertificatDTO);
        if (templateCertificatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateCertificatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateCertificatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplateCertificatDTO> result = templateCertificatService.partialUpdate(templateCertificatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateCertificatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /template-certificats} : get all the templateCertificats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateCertificats in body.
     */
    @GetMapping("/template-certificats")
    public ResponseEntity<List<TemplateCertificatDTO>> getAllTemplateCertificats(Pageable pageable) {
        log.debug("REST request to get a page of TemplateCertificats");
        Page<TemplateCertificatDTO> page = templateCertificatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-certificats/:id} : get the "id" templateCertificat.
     *
     * @param id the id of the templateCertificatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateCertificatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-certificats/{id}")
    public ResponseEntity<TemplateCertificatDTO> getTemplateCertificat(@PathVariable Long id) {
        log.debug("REST request to get TemplateCertificat : {}", id);
        Optional<TemplateCertificatDTO> templateCertificatDTO = templateCertificatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateCertificatDTO);
    }

    /**
     * {@code DELETE  /template-certificats/:id} : delete the "id" templateCertificat.
     *
     * @param id the id of the templateCertificatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-certificats/{id}")
    public ResponseEntity<Void> deleteTemplateCertificat(@PathVariable Long id) {
        log.debug("REST request to delete TemplateCertificat : {}", id);
        templateCertificatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
