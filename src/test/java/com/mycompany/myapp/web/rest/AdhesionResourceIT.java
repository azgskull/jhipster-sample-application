package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Adhesion;
import com.mycompany.myapp.repository.AdhesionRepository;
import com.mycompany.myapp.service.dto.AdhesionDTO;
import com.mycompany.myapp.service.mapper.AdhesionMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdhesionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdhesionResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/adhesions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdhesionRepository adhesionRepository;

    @Autowired
    private AdhesionMapper adhesionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdhesionMockMvc;

    private Adhesion adhesion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adhesion createEntity(EntityManager em) {
        Adhesion adhesion = new Adhesion().dateDebut(DEFAULT_DATE_DEBUT).dateFin(DEFAULT_DATE_FIN);
        return adhesion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adhesion createUpdatedEntity(EntityManager em) {
        Adhesion adhesion = new Adhesion().dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);
        return adhesion;
    }

    @BeforeEach
    public void initTest() {
        adhesion = createEntity(em);
    }

    @Test
    @Transactional
    void createAdhesion() throws Exception {
        int databaseSizeBeforeCreate = adhesionRepository.findAll().size();
        // Create the Adhesion
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);
        restAdhesionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhesionDTO)))
            .andExpect(status().isCreated());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeCreate + 1);
        Adhesion testAdhesion = adhesionList.get(adhesionList.size() - 1);
        assertThat(testAdhesion.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAdhesion.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void createAdhesionWithExistingId() throws Exception {
        // Create the Adhesion with an existing ID
        adhesion.setId(1L);
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        int databaseSizeBeforeCreate = adhesionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdhesionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhesionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = adhesionRepository.findAll().size();
        // set the field null
        adhesion.setDateDebut(null);

        // Create the Adhesion, which fails.
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        restAdhesionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhesionDTO)))
            .andExpect(status().isBadRequest());

        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdhesions() throws Exception {
        // Initialize the database
        adhesionRepository.saveAndFlush(adhesion);

        // Get all the adhesionList
        restAdhesionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adhesion.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    @Test
    @Transactional
    void getAdhesion() throws Exception {
        // Initialize the database
        adhesionRepository.saveAndFlush(adhesion);

        // Get the adhesion
        restAdhesionMockMvc
            .perform(get(ENTITY_API_URL_ID, adhesion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adhesion.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdhesion() throws Exception {
        // Get the adhesion
        restAdhesionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdhesion() throws Exception {
        // Initialize the database
        adhesionRepository.saveAndFlush(adhesion);

        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();

        // Update the adhesion
        Adhesion updatedAdhesion = adhesionRepository.findById(adhesion.getId()).get();
        // Disconnect from session so that the updates on updatedAdhesion are not directly saved in db
        em.detach(updatedAdhesion);
        updatedAdhesion.dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(updatedAdhesion);

        restAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adhesionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adhesionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
        Adhesion testAdhesion = adhesionList.get(adhesionList.size() - 1);
        assertThat(testAdhesion.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAdhesion.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void putNonExistingAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();
        adhesion.setId(count.incrementAndGet());

        // Create the Adhesion
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adhesionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adhesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();
        adhesion.setId(count.incrementAndGet());

        // Create the Adhesion
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adhesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();
        adhesion.setId(count.incrementAndGet());

        // Create the Adhesion
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhesionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhesionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdhesionWithPatch() throws Exception {
        // Initialize the database
        adhesionRepository.saveAndFlush(adhesion);

        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();

        // Update the adhesion using partial update
        Adhesion partialUpdatedAdhesion = new Adhesion();
        partialUpdatedAdhesion.setId(adhesion.getId());

        partialUpdatedAdhesion.dateFin(UPDATED_DATE_FIN);

        restAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
        Adhesion testAdhesion = adhesionList.get(adhesionList.size() - 1);
        assertThat(testAdhesion.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAdhesion.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void fullUpdateAdhesionWithPatch() throws Exception {
        // Initialize the database
        adhesionRepository.saveAndFlush(adhesion);

        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();

        // Update the adhesion using partial update
        Adhesion partialUpdatedAdhesion = new Adhesion();
        partialUpdatedAdhesion.setId(adhesion.getId());

        partialUpdatedAdhesion.dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
        Adhesion testAdhesion = adhesionList.get(adhesionList.size() - 1);
        assertThat(testAdhesion.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAdhesion.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();
        adhesion.setId(count.incrementAndGet());

        // Create the Adhesion
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adhesionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adhesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();
        adhesion.setId(count.incrementAndGet());

        // Create the Adhesion
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adhesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = adhesionRepository.findAll().size();
        adhesion.setId(count.incrementAndGet());

        // Create the Adhesion
        AdhesionDTO adhesionDTO = adhesionMapper.toDto(adhesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adhesionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adhesion in the database
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdhesion() throws Exception {
        // Initialize the database
        adhesionRepository.saveAndFlush(adhesion);

        int databaseSizeBeforeDelete = adhesionRepository.findAll().size();

        // Delete the adhesion
        restAdhesionMockMvc
            .perform(delete(ENTITY_API_URL_ID, adhesion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adhesion> adhesionList = adhesionRepository.findAll();
        assertThat(adhesionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
