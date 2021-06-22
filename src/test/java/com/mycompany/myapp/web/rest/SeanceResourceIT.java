package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Seance;
import com.mycompany.myapp.repository.SeanceRepository;
import com.mycompany.myapp.service.dto.SeanceDTO;
import com.mycompany.myapp.service.mapper.SeanceMapper;
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
 * Integration tests for the {@link SeanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeanceResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HEURE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_FIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/seances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private SeanceMapper seanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeanceMockMvc;

    private Seance seance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seance createEntity(EntityManager em) {
        Seance seance = new Seance()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .heureDebut(DEFAULT_HEURE_DEBUT)
            .heureFin(DEFAULT_HEURE_FIN);
        return seance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seance createUpdatedEntity(EntityManager em) {
        Seance seance = new Seance()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN);
        return seance;
    }

    @BeforeEach
    public void initTest() {
        seance = createEntity(em);
    }

    @Test
    @Transactional
    void createSeance() throws Exception {
        int databaseSizeBeforeCreate = seanceRepository.findAll().size();
        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);
        restSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate + 1);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSeance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSeance.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSeance.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testSeance.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
    }

    @Test
    @Transactional
    void createSeanceWithExistingId() throws Exception {
        // Create the Seance with an existing ID
        seance.setId(1L);
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        int databaseSizeBeforeCreate = seanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = seanceRepository.findAll().size();
        // set the field null
        seance.setNom(null);

        // Create the Seance, which fails.
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        restSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = seanceRepository.findAll().size();
        // set the field null
        seance.setDate(null);

        // Create the Seance, which fails.
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        restSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSeances() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get all the seanceList
        restSeanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seance.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].heureDebut").value(hasItem(DEFAULT_HEURE_DEBUT)))
            .andExpect(jsonPath("$.[*].heureFin").value(hasItem(DEFAULT_HEURE_FIN)));
    }

    @Test
    @Transactional
    void getSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get the seance
        restSeanceMockMvc
            .perform(get(ENTITY_API_URL_ID, seance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seance.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.heureDebut").value(DEFAULT_HEURE_DEBUT))
            .andExpect(jsonPath("$.heureFin").value(DEFAULT_HEURE_FIN));
    }

    @Test
    @Transactional
    void getNonExistingSeance() throws Exception {
        // Get the seance
        restSeanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Update the seance
        Seance updatedSeance = seanceRepository.findById(seance.getId()).get();
        // Disconnect from session so that the updates on updatedSeance are not directly saved in db
        em.detach(updatedSeance);
        updatedSeance
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN);
        SeanceDTO seanceDTO = seanceMapper.toDto(updatedSeance);

        restSeanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSeance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSeance.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testSeance.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
    }

    @Test
    @Transactional
    void putNonExistingSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();
        seance.setId(count.incrementAndGet());

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();
        seance.setId(count.incrementAndGet());

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();
        seance.setId(count.incrementAndGet());

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeanceWithPatch() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Update the seance using partial update
        Seance partialUpdatedSeance = new Seance();
        partialUpdatedSeance.setId(seance.getId());

        partialUpdatedSeance.description(UPDATED_DESCRIPTION).date(UPDATED_DATE).heureDebut(UPDATED_HEURE_DEBUT);

        restSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeance))
            )
            .andExpect(status().isOk());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSeance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSeance.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testSeance.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
    }

    @Test
    @Transactional
    void fullUpdateSeanceWithPatch() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Update the seance using partial update
        Seance partialUpdatedSeance = new Seance();
        partialUpdatedSeance.setId(seance.getId());

        partialUpdatedSeance
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN);

        restSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeance))
            )
            .andExpect(status().isOk());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSeance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSeance.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testSeance.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();
        seance.setId(count.incrementAndGet());

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();
        seance.setId(count.incrementAndGet());

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();
        seance.setId(count.incrementAndGet());

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeDelete = seanceRepository.findAll().size();

        // Delete the seance
        restSeanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, seance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
