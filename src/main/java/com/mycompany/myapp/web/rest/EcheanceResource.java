package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EcheanceRepository;
import com.mycompany.myapp.service.EcheanceService;
import com.mycompany.myapp.service.dto.EcheanceDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Echeance}.
 */
@RestController
@RequestMapping("/api")
public class EcheanceResource {

    private final Logger log = LoggerFactory.getLogger(EcheanceResource.class);

    private static final String ENTITY_NAME = "echeance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcheanceService echeanceService;

    private final EcheanceRepository echeanceRepository;

    public EcheanceResource(EcheanceService echeanceService, EcheanceRepository echeanceRepository) {
        this.echeanceService = echeanceService;
        this.echeanceRepository = echeanceRepository;
    }

    /**
     * {@code POST  /echeances} : Create a new echeance.
     *
     * @param echeanceDTO the echeanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new echeanceDTO, or with status {@code 400 (Bad Request)} if the echeance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/echeances")
    public ResponseEntity<EcheanceDTO> createEcheance(@Valid @RequestBody EcheanceDTO echeanceDTO) throws URISyntaxException {
        log.debug("REST request to save Echeance : {}", echeanceDTO);
        if (echeanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new echeance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EcheanceDTO result = echeanceService.save(echeanceDTO);
        return ResponseEntity
            .created(new URI("/api/echeances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /echeances/:id} : Updates an existing echeance.
     *
     * @param id the id of the echeanceDTO to save.
     * @param echeanceDTO the echeanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated echeanceDTO,
     * or with status {@code 400 (Bad Request)} if the echeanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the echeanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/echeances/{id}")
    public ResponseEntity<EcheanceDTO> updateEcheance(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EcheanceDTO echeanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Echeance : {}, {}", id, echeanceDTO);
        if (echeanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, echeanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!echeanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EcheanceDTO result = echeanceService.save(echeanceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, echeanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /echeances/:id} : Partial updates given fields of an existing echeance, field will ignore if it is null
     *
     * @param id the id of the echeanceDTO to save.
     * @param echeanceDTO the echeanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated echeanceDTO,
     * or with status {@code 400 (Bad Request)} if the echeanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the echeanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the echeanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/echeances/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EcheanceDTO> partialUpdateEcheance(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EcheanceDTO echeanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Echeance partially : {}, {}", id, echeanceDTO);
        if (echeanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, echeanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!echeanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EcheanceDTO> result = echeanceService.partialUpdate(echeanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, echeanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /echeances} : get all the echeances.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of echeances in body.
     */
    @GetMapping("/echeances")
    public ResponseEntity<List<EcheanceDTO>> getAllEcheances(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Echeances");
        Page<EcheanceDTO> page;
        if (eagerload) {
            page = echeanceService.findAllWithEagerRelationships(pageable);
        } else {
            page = echeanceService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /echeances/:id} : get the "id" echeance.
     *
     * @param id the id of the echeanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the echeanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/echeances/{id}")
    public ResponseEntity<EcheanceDTO> getEcheance(@PathVariable Long id) {
        log.debug("REST request to get Echeance : {}", id);
        Optional<EcheanceDTO> echeanceDTO = echeanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(echeanceDTO);
    }

    /**
     * {@code DELETE  /echeances/:id} : delete the "id" echeance.
     *
     * @param id the id of the echeanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/echeances/{id}")
    public ResponseEntity<Void> deleteEcheance(@PathVariable Long id) {
        log.debug("REST request to delete Echeance : {}", id);
        echeanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
