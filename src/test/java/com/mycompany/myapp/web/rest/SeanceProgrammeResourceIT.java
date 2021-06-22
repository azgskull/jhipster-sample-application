package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.SeanceProgramme;
import com.mycompany.myapp.repository.SeanceProgrammeRepository;
import com.mycompany.myapp.service.dto.SeanceProgrammeDTO;
import com.mycompany.myapp.service.mapper.SeanceProgrammeMapper;
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
 * Integration tests for the {@link SeanceProgrammeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeanceProgrammeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAMME_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAMME_EXPRESSION = "BBBBBBBBBB";

    private static final Double DEFAULT_DUREE = 1D;
    private static final Double UPDATED_DUREE = 2D;

    private static final String ENTITY_API_URL = "/api/seance-programmes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeanceProgrammeRepository seanceProgrammeRepository;

    @Autowired
    private SeanceProgrammeMapper seanceProgrammeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeanceProgrammeMockMvc;

    private SeanceProgramme seanceProgramme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeanceProgramme createEntity(EntityManager em) {
        SeanceProgramme seanceProgramme = new SeanceProgramme()
            .code(DEFAULT_CODE)
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .programmeExpression(DEFAULT_PROGRAMME_EXPRESSION)
            .duree(DEFAULT_DUREE);
        return seanceProgramme;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeanceProgramme createUpdatedEntity(EntityManager em) {
        SeanceProgramme seanceProgramme = new SeanceProgramme()
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .programmeExpression(UPDATED_PROGRAMME_EXPRESSION)
            .duree(UPDATED_DUREE);
        return seanceProgramme;
    }

    @BeforeEach
    public void initTest() {
        seanceProgramme = createEntity(em);
    }

    @Test
    @Transactional
    void createSeanceProgramme() throws Exception {
        int databaseSizeBeforeCreate = seanceProgrammeRepository.findAll().size();
        // Create the SeanceProgramme
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);
        restSeanceProgrammeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeCreate + 1);
        SeanceProgramme testSeanceProgramme = seanceProgrammeList.get(seanceProgrammeList.size() - 1);
        assertThat(testSeanceProgramme.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSeanceProgramme.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSeanceProgramme.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSeanceProgramme.getProgrammeExpression()).isEqualTo(DEFAULT_PROGRAMME_EXPRESSION);
        assertThat(testSeanceProgramme.getDuree()).isEqualTo(DEFAULT_DUREE);
    }

    @Test
    @Transactional
    void createSeanceProgrammeWithExistingId() throws Exception {
        // Create the SeanceProgramme with an existing ID
        seanceProgramme.setId(1L);
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        int databaseSizeBeforeCreate = seanceProgrammeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeanceProgrammeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seanceProgrammeRepository.findAll().size();
        // set the field null
        seanceProgramme.setCode(null);

        // Create the SeanceProgramme, which fails.
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        restSeanceProgrammeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isBadRequest());

        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = seanceProgrammeRepository.findAll().size();
        // set the field null
        seanceProgramme.setNom(null);

        // Create the SeanceProgramme, which fails.
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        restSeanceProgrammeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isBadRequest());

        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSeanceProgrammes() throws Exception {
        // Initialize the database
        seanceProgrammeRepository.saveAndFlush(seanceProgramme);

        // Get all the seanceProgrammeList
        restSeanceProgrammeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seanceProgramme.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].programmeExpression").value(hasItem(DEFAULT_PROGRAMME_EXPRESSION)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.doubleValue())));
    }

    @Test
    @Transactional
    void getSeanceProgramme() throws Exception {
        // Initialize the database
        seanceProgrammeRepository.saveAndFlush(seanceProgramme);

        // Get the seanceProgramme
        restSeanceProgrammeMockMvc
            .perform(get(ENTITY_API_URL_ID, seanceProgramme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seanceProgramme.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.programmeExpression").value(DEFAULT_PROGRAMME_EXPRESSION))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSeanceProgramme() throws Exception {
        // Get the seanceProgramme
        restSeanceProgrammeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeanceProgramme() throws Exception {
        // Initialize the database
        seanceProgrammeRepository.saveAndFlush(seanceProgramme);

        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();

        // Update the seanceProgramme
        SeanceProgramme updatedSeanceProgramme = seanceProgrammeRepository.findById(seanceProgramme.getId()).get();
        // Disconnect from session so that the updates on updatedSeanceProgramme are not directly saved in db
        em.detach(updatedSeanceProgramme);
        updatedSeanceProgramme
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .programmeExpression(UPDATED_PROGRAMME_EXPRESSION)
            .duree(UPDATED_DUREE);
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(updatedSeanceProgramme);

        restSeanceProgrammeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seanceProgrammeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isOk());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
        SeanceProgramme testSeanceProgramme = seanceProgrammeList.get(seanceProgrammeList.size() - 1);
        assertThat(testSeanceProgramme.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSeanceProgramme.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSeanceProgramme.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeanceProgramme.getProgrammeExpression()).isEqualTo(UPDATED_PROGRAMME_EXPRESSION);
        assertThat(testSeanceProgramme.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    void putNonExistingSeanceProgramme() throws Exception {
        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();
        seanceProgramme.setId(count.incrementAndGet());

        // Create the SeanceProgramme
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeanceProgrammeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seanceProgrammeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeanceProgramme() throws Exception {
        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();
        seanceProgramme.setId(count.incrementAndGet());

        // Create the SeanceProgramme
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceProgrammeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeanceProgramme() throws Exception {
        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();
        seanceProgramme.setId(count.incrementAndGet());

        // Create the SeanceProgramme
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceProgrammeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeanceProgrammeWithPatch() throws Exception {
        // Initialize the database
        seanceProgrammeRepository.saveAndFlush(seanceProgramme);

        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();

        // Update the seanceProgramme using partial update
        SeanceProgramme partialUpdatedSeanceProgramme = new SeanceProgramme();
        partialUpdatedSeanceProgramme.setId(seanceProgramme.getId());

        partialUpdatedSeanceProgramme.code(UPDATED_CODE).nom(UPDATED_NOM).programmeExpression(UPDATED_PROGRAMME_EXPRESSION);

        restSeanceProgrammeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeanceProgramme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeanceProgramme))
            )
            .andExpect(status().isOk());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
        SeanceProgramme testSeanceProgramme = seanceProgrammeList.get(seanceProgrammeList.size() - 1);
        assertThat(testSeanceProgramme.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSeanceProgramme.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSeanceProgramme.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSeanceProgramme.getProgrammeExpression()).isEqualTo(UPDATED_PROGRAMME_EXPRESSION);
        assertThat(testSeanceProgramme.getDuree()).isEqualTo(DEFAULT_DUREE);
    }

    @Test
    @Transactional
    void fullUpdateSeanceProgrammeWithPatch() throws Exception {
        // Initialize the database
        seanceProgrammeRepository.saveAndFlush(seanceProgramme);

        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();

        // Update the seanceProgramme using partial update
        SeanceProgramme partialUpdatedSeanceProgramme = new SeanceProgramme();
        partialUpdatedSeanceProgramme.setId(seanceProgramme.getId());

        partialUpdatedSeanceProgramme
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .programmeExpression(UPDATED_PROGRAMME_EXPRESSION)
            .duree(UPDATED_DUREE);

        restSeanceProgrammeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeanceProgramme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeanceProgramme))
            )
            .andExpect(status().isOk());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
        SeanceProgramme testSeanceProgramme = seanceProgrammeList.get(seanceProgrammeList.size() - 1);
        assertThat(testSeanceProgramme.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSeanceProgramme.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSeanceProgramme.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeanceProgramme.getProgrammeExpression()).isEqualTo(UPDATED_PROGRAMME_EXPRESSION);
        assertThat(testSeanceProgramme.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    void patchNonExistingSeanceProgramme() throws Exception {
        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();
        seanceProgramme.setId(count.incrementAndGet());

        // Create the SeanceProgramme
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeanceProgrammeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seanceProgrammeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeanceProgramme() throws Exception {
        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();
        seanceProgramme.setId(count.incrementAndGet());

        // Create the SeanceProgramme
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceProgrammeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeanceProgramme() throws Exception {
        int databaseSizeBeforeUpdate = seanceProgrammeRepository.findAll().size();
        seanceProgramme.setId(count.incrementAndGet());

        // Create the SeanceProgramme
        SeanceProgrammeDTO seanceProgrammeDTO = seanceProgrammeMapper.toDto(seanceProgramme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceProgrammeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seanceProgrammeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeanceProgramme in the database
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeanceProgramme() throws Exception {
        // Initialize the database
        seanceProgrammeRepository.saveAndFlush(seanceProgramme);

        int databaseSizeBeforeDelete = seanceProgrammeRepository.findAll().size();

        // Delete the seanceProgramme
        restSeanceProgrammeMockMvc
            .perform(delete(ENTITY_API_URL_ID, seanceProgramme.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeanceProgramme> seanceProgrammeList = seanceProgrammeRepository.findAll();
        assertThat(seanceProgrammeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
