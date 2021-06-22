package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.OrganismeAssuranceRepository;
import com.mycompany.myapp.service.OrganismeAssuranceService;
import com.mycompany.myapp.service.dto.OrganismeAssuranceDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.OrganismeAssurance}.
 */
@RestController
@RequestMapping("/api")
public class OrganismeAssuranceResource {

    private final Logger log = LoggerFactory.getLogger(OrganismeAssuranceResource.class);

    private static final String ENTITY_NAME = "organismeAssurance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganismeAssuranceService organismeAssuranceService;

    private final OrganismeAssuranceRepository organismeAssuranceRepository;

    public OrganismeAssuranceResource(
        OrganismeAssuranceService organismeAssuranceService,
        OrganismeAssuranceRepository organismeAssuranceRepository
    ) {
        this.organismeAssuranceService = organismeAssuranceService;
        this.organismeAssuranceRepository = organismeAssuranceRepository;
    }

    /**
     * {@code POST  /organisme-assurances} : Create a new organismeAssurance.
     *
     * @param organismeAssuranceDTO the organismeAssuranceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organismeAssuranceDTO, or with status {@code 400 (Bad Request)} if the organismeAssurance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisme-assurances")
    public ResponseEntity<OrganismeAssuranceDTO> createOrganismeAssurance(@Valid @RequestBody OrganismeAssuranceDTO organismeAssuranceDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrganismeAssurance : {}", organismeAssuranceDTO);
        if (organismeAssuranceDTO.getId() != null) {
            throw new BadRequestAlertException("A new organismeAssurance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganismeAssuranceDTO result = organismeAssuranceService.save(organismeAssuranceDTO);
        return ResponseEntity
            .created(new URI("/api/organisme-assurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisme-assurances/:id} : Updates an existing organismeAssurance.
     *
     * @param id the id of the organismeAssuranceDTO to save.
     * @param organismeAssuranceDTO the organismeAssuranceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organismeAssuranceDTO,
     * or with status {@code 400 (Bad Request)} if the organismeAssuranceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organismeAssuranceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisme-assurances/{id}")
    public ResponseEntity<OrganismeAssuranceDTO> updateOrganismeAssurance(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganismeAssuranceDTO organismeAssuranceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganismeAssurance : {}, {}", id, organismeAssuranceDTO);
        if (organismeAssuranceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organismeAssuranceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organismeAssuranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganismeAssuranceDTO result = organismeAssuranceService.save(organismeAssuranceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organismeAssuranceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organisme-assurances/:id} : Partial updates given fields of an existing organismeAssurance, field will ignore if it is null
     *
     * @param id the id of the organismeAssuranceDTO to save.
     * @param organismeAssuranceDTO the organismeAssuranceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organismeAssuranceDTO,
     * or with status {@code 400 (Bad Request)} if the organismeAssuranceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organismeAssuranceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organismeAssuranceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organisme-assurances/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrganismeAssuranceDTO> partialUpdateOrganismeAssurance(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganismeAssuranceDTO organismeAssuranceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganismeAssurance partially : {}, {}", id, organismeAssuranceDTO);
        if (organismeAssuranceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organismeAssuranceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organismeAssuranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganismeAssuranceDTO> result = organismeAssuranceService.partialUpdate(organismeAssuranceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organismeAssuranceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organisme-assurances} : get all the organismeAssurances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organismeAssurances in body.
     */
    @GetMapping("/organisme-assurances")
    public ResponseEntity<List<OrganismeAssuranceDTO>> getAllOrganismeAssurances(Pageable pageable) {
        log.debug("REST request to get a page of OrganismeAssurances");
        Page<OrganismeAssuranceDTO> page = organismeAssuranceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organisme-assurances/:id} : get the "id" organismeAssurance.
     *
     * @param id the id of the organismeAssuranceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organismeAssuranceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisme-assurances/{id}")
    public ResponseEntity<OrganismeAssuranceDTO> getOrganismeAssurance(@PathVariable Long id) {
        log.debug("REST request to get OrganismeAssurance : {}", id);
        Optional<OrganismeAssuranceDTO> organismeAssuranceDTO = organismeAssuranceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organismeAssuranceDTO);
    }

    /**
     * {@code DELETE  /organisme-assurances/:id} : delete the "id" organismeAssurance.
     *
     * @param id the id of the organismeAssuranceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisme-assurances/{id}")
    public ResponseEntity<Void> deleteOrganismeAssurance(@PathVariable Long id) {
        log.debug("REST request to delete OrganismeAssurance : {}", id);
        organismeAssuranceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
