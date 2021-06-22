package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TypeSeanceRepository;
import com.mycompany.myapp.service.TypeSeanceService;
import com.mycompany.myapp.service.dto.TypeSeanceDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TypeSeance}.
 */
@RestController
@RequestMapping("/api")
public class TypeSeanceResource {

    private final Logger log = LoggerFactory.getLogger(TypeSeanceResource.class);

    private static final String ENTITY_NAME = "typeSeance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeSeanceService typeSeanceService;

    private final TypeSeanceRepository typeSeanceRepository;

    public TypeSeanceResource(TypeSeanceService typeSeanceService, TypeSeanceRepository typeSeanceRepository) {
        this.typeSeanceService = typeSeanceService;
        this.typeSeanceRepository = typeSeanceRepository;
    }

    /**
     * {@code POST  /type-seances} : Create a new typeSeance.
     *
     * @param typeSeanceDTO the typeSeanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeSeanceDTO, or with status {@code 400 (Bad Request)} if the typeSeance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-seances")
    public ResponseEntity<TypeSeanceDTO> createTypeSeance(@Valid @RequestBody TypeSeanceDTO typeSeanceDTO) throws URISyntaxException {
        log.debug("REST request to save TypeSeance : {}", typeSeanceDTO);
        if (typeSeanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeSeance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeSeanceDTO result = typeSeanceService.save(typeSeanceDTO);
        return ResponseEntity
            .created(new URI("/api/type-seances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-seances/:id} : Updates an existing typeSeance.
     *
     * @param id the id of the typeSeanceDTO to save.
     * @param typeSeanceDTO the typeSeanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeSeanceDTO,
     * or with status {@code 400 (Bad Request)} if the typeSeanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeSeanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-seances/{id}")
    public ResponseEntity<TypeSeanceDTO> updateTypeSeance(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeSeanceDTO typeSeanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeSeance : {}, {}", id, typeSeanceDTO);
        if (typeSeanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeSeanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeSeanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeSeanceDTO result = typeSeanceService.save(typeSeanceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeSeanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-seances/:id} : Partial updates given fields of an existing typeSeance, field will ignore if it is null
     *
     * @param id the id of the typeSeanceDTO to save.
     * @param typeSeanceDTO the typeSeanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeSeanceDTO,
     * or with status {@code 400 (Bad Request)} if the typeSeanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeSeanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeSeanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-seances/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TypeSeanceDTO> partialUpdateTypeSeance(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeSeanceDTO typeSeanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeSeance partially : {}, {}", id, typeSeanceDTO);
        if (typeSeanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeSeanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeSeanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeSeanceDTO> result = typeSeanceService.partialUpdate(typeSeanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeSeanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-seances} : get all the typeSeances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeSeances in body.
     */
    @GetMapping("/type-seances")
    public ResponseEntity<List<TypeSeanceDTO>> getAllTypeSeances(Pageable pageable) {
        log.debug("REST request to get a page of TypeSeances");
        Page<TypeSeanceDTO> page = typeSeanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-seances/:id} : get the "id" typeSeance.
     *
     * @param id the id of the typeSeanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeSeanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-seances/{id}")
    public ResponseEntity<TypeSeanceDTO> getTypeSeance(@PathVariable Long id) {
        log.debug("REST request to get TypeSeance : {}", id);
        Optional<TypeSeanceDTO> typeSeanceDTO = typeSeanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeSeanceDTO);
    }

    /**
     * {@code DELETE  /type-seances/:id} : delete the "id" typeSeance.
     *
     * @param id the id of the typeSeanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-seances/{id}")
    public ResponseEntity<Void> deleteTypeSeance(@PathVariable Long id) {
        log.debug("REST request to delete TypeSeance : {}", id);
        typeSeanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
