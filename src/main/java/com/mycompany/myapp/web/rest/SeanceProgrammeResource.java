package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SeanceProgrammeRepository;
import com.mycompany.myapp.service.SeanceProgrammeService;
import com.mycompany.myapp.service.dto.SeanceProgrammeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SeanceProgramme}.
 */
@RestController
@RequestMapping("/api")
public class SeanceProgrammeResource {

    private final Logger log = LoggerFactory.getLogger(SeanceProgrammeResource.class);

    private static final String ENTITY_NAME = "seanceProgramme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeanceProgrammeService seanceProgrammeService;

    private final SeanceProgrammeRepository seanceProgrammeRepository;

    public SeanceProgrammeResource(SeanceProgrammeService seanceProgrammeService, SeanceProgrammeRepository seanceProgrammeRepository) {
        this.seanceProgrammeService = seanceProgrammeService;
        this.seanceProgrammeRepository = seanceProgrammeRepository;
    }

    /**
     * {@code POST  /seance-programmes} : Create a new seanceProgramme.
     *
     * @param seanceProgrammeDTO the seanceProgrammeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seanceProgrammeDTO, or with status {@code 400 (Bad Request)} if the seanceProgramme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seance-programmes")
    public ResponseEntity<SeanceProgrammeDTO> createSeanceProgramme(@Valid @RequestBody SeanceProgrammeDTO seanceProgrammeDTO)
        throws URISyntaxException {
        log.debug("REST request to save SeanceProgramme : {}", seanceProgrammeDTO);
        if (seanceProgrammeDTO.getId() != null) {
            throw new BadRequestAlertException("A new seanceProgramme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeanceProgrammeDTO result = seanceProgrammeService.save(seanceProgrammeDTO);
        return ResponseEntity
            .created(new URI("/api/seance-programmes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seance-programmes/:id} : Updates an existing seanceProgramme.
     *
     * @param id the id of the seanceProgrammeDTO to save.
     * @param seanceProgrammeDTO the seanceProgrammeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seanceProgrammeDTO,
     * or with status {@code 400 (Bad Request)} if the seanceProgrammeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seanceProgrammeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seance-programmes/{id}")
    public ResponseEntity<SeanceProgrammeDTO> updateSeanceProgramme(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SeanceProgrammeDTO seanceProgrammeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SeanceProgramme : {}, {}", id, seanceProgrammeDTO);
        if (seanceProgrammeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seanceProgrammeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seanceProgrammeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SeanceProgrammeDTO result = seanceProgrammeService.save(seanceProgrammeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seanceProgrammeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seance-programmes/:id} : Partial updates given fields of an existing seanceProgramme, field will ignore if it is null
     *
     * @param id the id of the seanceProgrammeDTO to save.
     * @param seanceProgrammeDTO the seanceProgrammeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seanceProgrammeDTO,
     * or with status {@code 400 (Bad Request)} if the seanceProgrammeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the seanceProgrammeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the seanceProgrammeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seance-programmes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SeanceProgrammeDTO> partialUpdateSeanceProgramme(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SeanceProgrammeDTO seanceProgrammeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SeanceProgramme partially : {}, {}", id, seanceProgrammeDTO);
        if (seanceProgrammeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seanceProgrammeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seanceProgrammeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeanceProgrammeDTO> result = seanceProgrammeService.partialUpdate(seanceProgrammeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seanceProgrammeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /seance-programmes} : get all the seanceProgrammes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seanceProgrammes in body.
     */
    @GetMapping("/seance-programmes")
    public ResponseEntity<List<SeanceProgrammeDTO>> getAllSeanceProgrammes(Pageable pageable) {
        log.debug("REST request to get a page of SeanceProgrammes");
        Page<SeanceProgrammeDTO> page = seanceProgrammeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seance-programmes/:id} : get the "id" seanceProgramme.
     *
     * @param id the id of the seanceProgrammeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seanceProgrammeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seance-programmes/{id}")
    public ResponseEntity<SeanceProgrammeDTO> getSeanceProgramme(@PathVariable Long id) {
        log.debug("REST request to get SeanceProgramme : {}", id);
        Optional<SeanceProgrammeDTO> seanceProgrammeDTO = seanceProgrammeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seanceProgrammeDTO);
    }

    /**
     * {@code DELETE  /seance-programmes/:id} : delete the "id" seanceProgramme.
     *
     * @param id the id of the seanceProgrammeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seance-programmes/{id}")
    public ResponseEntity<Void> deleteSeanceProgramme(@PathVariable Long id) {
        log.debug("REST request to delete SeanceProgramme : {}", id);
        seanceProgrammeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
