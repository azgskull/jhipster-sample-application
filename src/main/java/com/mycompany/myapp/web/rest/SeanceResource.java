package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SeanceRepository;
import com.mycompany.myapp.service.SeanceService;
import com.mycompany.myapp.service.dto.SeanceDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Seance}.
 */
@RestController
@RequestMapping("/api")
public class SeanceResource {

    private final Logger log = LoggerFactory.getLogger(SeanceResource.class);

    private static final String ENTITY_NAME = "seance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeanceService seanceService;

    private final SeanceRepository seanceRepository;

    public SeanceResource(SeanceService seanceService, SeanceRepository seanceRepository) {
        this.seanceService = seanceService;
        this.seanceRepository = seanceRepository;
    }

    /**
     * {@code POST  /seances} : Create a new seance.
     *
     * @param seanceDTO the seanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seanceDTO, or with status {@code 400 (Bad Request)} if the seance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seances")
    public ResponseEntity<SeanceDTO> createSeance(@Valid @RequestBody SeanceDTO seanceDTO) throws URISyntaxException {
        log.debug("REST request to save Seance : {}", seanceDTO);
        if (seanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new seance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeanceDTO result = seanceService.save(seanceDTO);
        return ResponseEntity
            .created(new URI("/api/seances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seances/:id} : Updates an existing seance.
     *
     * @param id the id of the seanceDTO to save.
     * @param seanceDTO the seanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seanceDTO,
     * or with status {@code 400 (Bad Request)} if the seanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seances/{id}")
    public ResponseEntity<SeanceDTO> updateSeance(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SeanceDTO seanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Seance : {}, {}", id, seanceDTO);
        if (seanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SeanceDTO result = seanceService.save(seanceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seances/:id} : Partial updates given fields of an existing seance, field will ignore if it is null
     *
     * @param id the id of the seanceDTO to save.
     * @param seanceDTO the seanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seanceDTO,
     * or with status {@code 400 (Bad Request)} if the seanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the seanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the seanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seances/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SeanceDTO> partialUpdateSeance(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SeanceDTO seanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Seance partially : {}, {}", id, seanceDTO);
        if (seanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeanceDTO> result = seanceService.partialUpdate(seanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /seances} : get all the seances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seances in body.
     */
    @GetMapping("/seances")
    public ResponseEntity<List<SeanceDTO>> getAllSeances(Pageable pageable) {
        log.debug("REST request to get a page of Seances");
        Page<SeanceDTO> page = seanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seances/:id} : get the "id" seance.
     *
     * @param id the id of the seanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seances/{id}")
    public ResponseEntity<SeanceDTO> getSeance(@PathVariable Long id) {
        log.debug("REST request to get Seance : {}", id);
        Optional<SeanceDTO> seanceDTO = seanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seanceDTO);
    }

    /**
     * {@code DELETE  /seances/:id} : delete the "id" seance.
     *
     * @param id the id of the seanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seances/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable Long id) {
        log.debug("REST request to delete Seance : {}", id);
        seanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
