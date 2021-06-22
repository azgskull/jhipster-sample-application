package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DisciplineRepository;
import com.mycompany.myapp.service.DisciplineService;
import com.mycompany.myapp.service.dto.DisciplineDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Discipline}.
 */
@RestController
@RequestMapping("/api")
public class DisciplineResource {

    private final Logger log = LoggerFactory.getLogger(DisciplineResource.class);

    private static final String ENTITY_NAME = "discipline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplineService disciplineService;

    private final DisciplineRepository disciplineRepository;

    public DisciplineResource(DisciplineService disciplineService, DisciplineRepository disciplineRepository) {
        this.disciplineService = disciplineService;
        this.disciplineRepository = disciplineRepository;
    }

    /**
     * {@code POST  /disciplines} : Create a new discipline.
     *
     * @param disciplineDTO the disciplineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplineDTO, or with status {@code 400 (Bad Request)} if the discipline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disciplines")
    public ResponseEntity<DisciplineDTO> createDiscipline(@Valid @RequestBody DisciplineDTO disciplineDTO) throws URISyntaxException {
        log.debug("REST request to save Discipline : {}", disciplineDTO);
        if (disciplineDTO.getId() != null) {
            throw new BadRequestAlertException("A new discipline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplineDTO result = disciplineService.save(disciplineDTO);
        return ResponseEntity
            .created(new URI("/api/disciplines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disciplines/:id} : Updates an existing discipline.
     *
     * @param id the id of the disciplineDTO to save.
     * @param disciplineDTO the disciplineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplineDTO,
     * or with status {@code 400 (Bad Request)} if the disciplineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disciplines/{id}")
    public ResponseEntity<DisciplineDTO> updateDiscipline(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DisciplineDTO disciplineDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Discipline : {}, {}", id, disciplineDTO);
        if (disciplineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DisciplineDTO result = disciplineService.save(disciplineDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /disciplines/:id} : Partial updates given fields of an existing discipline, field will ignore if it is null
     *
     * @param id the id of the disciplineDTO to save.
     * @param disciplineDTO the disciplineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplineDTO,
     * or with status {@code 400 (Bad Request)} if the disciplineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the disciplineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the disciplineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/disciplines/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DisciplineDTO> partialUpdateDiscipline(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DisciplineDTO disciplineDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Discipline partially : {}, {}", id, disciplineDTO);
        if (disciplineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DisciplineDTO> result = disciplineService.partialUpdate(disciplineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /disciplines} : get all the disciplines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplines in body.
     */
    @GetMapping("/disciplines")
    public ResponseEntity<List<DisciplineDTO>> getAllDisciplines(Pageable pageable) {
        log.debug("REST request to get a page of Disciplines");
        Page<DisciplineDTO> page = disciplineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disciplines/:id} : get the "id" discipline.
     *
     * @param id the id of the disciplineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disciplines/{id}")
    public ResponseEntity<DisciplineDTO> getDiscipline(@PathVariable Long id) {
        log.debug("REST request to get Discipline : {}", id);
        Optional<DisciplineDTO> disciplineDTO = disciplineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disciplineDTO);
    }

    /**
     * {@code DELETE  /disciplines/:id} : delete the "id" discipline.
     *
     * @param id the id of the disciplineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disciplines/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        log.debug("REST request to delete Discipline : {}", id);
        disciplineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
