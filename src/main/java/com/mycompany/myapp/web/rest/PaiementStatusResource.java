package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PaiementStatusRepository;
import com.mycompany.myapp.service.PaiementStatusService;
import com.mycompany.myapp.service.dto.PaiementStatusDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PaiementStatus}.
 */
@RestController
@RequestMapping("/api")
public class PaiementStatusResource {

    private final Logger log = LoggerFactory.getLogger(PaiementStatusResource.class);

    private static final String ENTITY_NAME = "paiementStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaiementStatusService paiementStatusService;

    private final PaiementStatusRepository paiementStatusRepository;

    public PaiementStatusResource(PaiementStatusService paiementStatusService, PaiementStatusRepository paiementStatusRepository) {
        this.paiementStatusService = paiementStatusService;
        this.paiementStatusRepository = paiementStatusRepository;
    }

    /**
     * {@code POST  /paiement-statuses} : Create a new paiementStatus.
     *
     * @param paiementStatusDTO the paiementStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paiementStatusDTO, or with status {@code 400 (Bad Request)} if the paiementStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paiement-statuses")
    public ResponseEntity<PaiementStatusDTO> createPaiementStatus(@Valid @RequestBody PaiementStatusDTO paiementStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaiementStatus : {}", paiementStatusDTO);
        if (paiementStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new paiementStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaiementStatusDTO result = paiementStatusService.save(paiementStatusDTO);
        return ResponseEntity
            .created(new URI("/api/paiement-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paiement-statuses/:id} : Updates an existing paiementStatus.
     *
     * @param id the id of the paiementStatusDTO to save.
     * @param paiementStatusDTO the paiementStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paiementStatusDTO,
     * or with status {@code 400 (Bad Request)} if the paiementStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paiementStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paiement-statuses/{id}")
    public ResponseEntity<PaiementStatusDTO> updatePaiementStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaiementStatusDTO paiementStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaiementStatus : {}, {}", id, paiementStatusDTO);
        if (paiementStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paiementStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paiementStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaiementStatusDTO result = paiementStatusService.save(paiementStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paiementStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paiement-statuses/:id} : Partial updates given fields of an existing paiementStatus, field will ignore if it is null
     *
     * @param id the id of the paiementStatusDTO to save.
     * @param paiementStatusDTO the paiementStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paiementStatusDTO,
     * or with status {@code 400 (Bad Request)} if the paiementStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paiementStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paiementStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paiement-statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PaiementStatusDTO> partialUpdatePaiementStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaiementStatusDTO paiementStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaiementStatus partially : {}, {}", id, paiementStatusDTO);
        if (paiementStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paiementStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paiementStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaiementStatusDTO> result = paiementStatusService.partialUpdate(paiementStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paiementStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /paiement-statuses} : get all the paiementStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paiementStatuses in body.
     */
    @GetMapping("/paiement-statuses")
    public ResponseEntity<List<PaiementStatusDTO>> getAllPaiementStatuses(Pageable pageable) {
        log.debug("REST request to get a page of PaiementStatuses");
        Page<PaiementStatusDTO> page = paiementStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paiement-statuses/:id} : get the "id" paiementStatus.
     *
     * @param id the id of the paiementStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paiementStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paiement-statuses/{id}")
    public ResponseEntity<PaiementStatusDTO> getPaiementStatus(@PathVariable Long id) {
        log.debug("REST request to get PaiementStatus : {}", id);
        Optional<PaiementStatusDTO> paiementStatusDTO = paiementStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paiementStatusDTO);
    }

    /**
     * {@code DELETE  /paiement-statuses/:id} : delete the "id" paiementStatus.
     *
     * @param id the id of the paiementStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paiement-statuses/{id}")
    public ResponseEntity<Void> deletePaiementStatus(@PathVariable Long id) {
        log.debug("REST request to delete PaiementStatus : {}", id);
        paiementStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
