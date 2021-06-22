package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SportifRepository;
import com.mycompany.myapp.service.SportifService;
import com.mycompany.myapp.service.dto.SportifDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Sportif}.
 */
@RestController
@RequestMapping("/api")
public class SportifResource {

    private final Logger log = LoggerFactory.getLogger(SportifResource.class);

    private static final String ENTITY_NAME = "sportif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportifService sportifService;

    private final SportifRepository sportifRepository;

    public SportifResource(SportifService sportifService, SportifRepository sportifRepository) {
        this.sportifService = sportifService;
        this.sportifRepository = sportifRepository;
    }

    /**
     * {@code POST  /sportifs} : Create a new sportif.
     *
     * @param sportifDTO the sportifDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportifDTO, or with status {@code 400 (Bad Request)} if the sportif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sportifs")
    public ResponseEntity<SportifDTO> createSportif(@Valid @RequestBody SportifDTO sportifDTO) throws URISyntaxException {
        log.debug("REST request to save Sportif : {}", sportifDTO);
        if (sportifDTO.getId() != null) {
            throw new BadRequestAlertException("A new sportif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SportifDTO result = sportifService.save(sportifDTO);
        return ResponseEntity
            .created(new URI("/api/sportifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sportifs/:id} : Updates an existing sportif.
     *
     * @param id the id of the sportifDTO to save.
     * @param sportifDTO the sportifDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportifDTO,
     * or with status {@code 400 (Bad Request)} if the sportifDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sportifDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sportifs/{id}")
    public ResponseEntity<SportifDTO> updateSportif(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SportifDTO sportifDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Sportif : {}, {}", id, sportifDTO);
        if (sportifDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportifDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportifRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SportifDTO result = sportifService.save(sportifDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sportifDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sportifs/:id} : Partial updates given fields of an existing sportif, field will ignore if it is null
     *
     * @param id the id of the sportifDTO to save.
     * @param sportifDTO the sportifDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportifDTO,
     * or with status {@code 400 (Bad Request)} if the sportifDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sportifDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sportifDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sportifs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SportifDTO> partialUpdateSportif(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SportifDTO sportifDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sportif partially : {}, {}", id, sportifDTO);
        if (sportifDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportifDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportifRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SportifDTO> result = sportifService.partialUpdate(sportifDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sportifDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sportifs} : get all the sportifs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sportifs in body.
     */
    @GetMapping("/sportifs")
    public ResponseEntity<List<SportifDTO>> getAllSportifs(Pageable pageable) {
        log.debug("REST request to get a page of Sportifs");
        Page<SportifDTO> page = sportifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sportifs/:id} : get the "id" sportif.
     *
     * @param id the id of the sportifDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportifDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sportifs/{id}")
    public ResponseEntity<SportifDTO> getSportif(@PathVariable Long id) {
        log.debug("REST request to get Sportif : {}", id);
        Optional<SportifDTO> sportifDTO = sportifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sportifDTO);
    }

    /**
     * {@code DELETE  /sportifs/:id} : delete the "id" sportif.
     *
     * @param id the id of the sportifDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sportifs/{id}")
    public ResponseEntity<Void> deleteSportif(@PathVariable Long id) {
        log.debug("REST request to delete Sportif : {}", id);
        sportifService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
