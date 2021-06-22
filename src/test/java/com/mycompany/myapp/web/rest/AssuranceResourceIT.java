package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Assurance;
import com.mycompany.myapp.repository.AssuranceRepository;
import com.mycompany.myapp.service.AssuranceService;
import com.mycompany.myapp.service.dto.AssuranceDTO;
import com.mycompany.myapp.service.mapper.AssuranceMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AssuranceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssuranceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/assurances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssuranceRepository assuranceRepository;

    @Mock
    private AssuranceRepository assuranceRepositoryMock;

    @Autowired
    private AssuranceMapper assuranceMapper;

    @Mock
    private AssuranceService assuranceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssuranceMockMvc;

    private Assurance assurance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assurance createEntity(EntityManager em) {
        Assurance assurance = new Assurance()
            .code(DEFAULT_CODE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .details(DEFAULT_DETAILS);
        return assurance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assurance createUpdatedEntity(EntityManager em) {
        Assurance assurance = new Assurance()
            .code(UPDATED_CODE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .details(UPDATED_DETAILS);
        return assurance;
    }

    @BeforeEach
    public void initTest() {
        assurance = createEntity(em);
    }

    @Test
    @Transactional
    void createAssurance() throws Exception {
        int databaseSizeBeforeCreate = assuranceRepository.findAll().size();
        // Create the Assurance
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);
        restAssuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeCreate + 1);
        Assurance testAssurance = assuranceList.get(assuranceList.size() - 1);
        assertThat(testAssurance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAssurance.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAssurance.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAssurance.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    void createAssuranceWithExistingId() throws Exception {
        // Create the Assurance with an existing ID
        assurance.setId(1L);
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        int databaseSizeBeforeCreate = assuranceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assuranceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assuranceRepository.findAll().size();
        // set the field null
        assurance.setCode(null);

        // Create the Assurance, which fails.
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        restAssuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assuranceDTO)))
            .andExpect(status().isBadRequest());

        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = assuranceRepository.findAll().size();
        // set the field null
        assurance.setDateDebut(null);

        // Create the Assurance, which fails.
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        restAssuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assuranceDTO)))
            .andExpect(status().isBadRequest());

        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = assuranceRepository.findAll().size();
        // set the field null
        assurance.setDateFin(null);

        // Create the Assurance, which fails.
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        restAssuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assuranceDTO)))
            .andExpect(status().isBadRequest());

        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssurances() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        // Get all the assuranceList
        restAssuranceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssurancesWithEagerRelationshipsIsEnabled() throws Exception {
        when(assuranceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssuranceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assuranceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssurancesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assuranceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssuranceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assuranceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAssurance() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        // Get the assurance
        restAssuranceMockMvc
            .perform(get(ENTITY_API_URL_ID, assurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assurance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    void getNonExistingAssurance() throws Exception {
        // Get the assurance
        restAssuranceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAssurance() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();

        // Update the assurance
        Assurance updatedAssurance = assuranceRepository.findById(assurance.getId()).get();
        // Disconnect from session so that the updates on updatedAssurance are not directly saved in db
        em.detach(updatedAssurance);
        updatedAssurance.code(UPDATED_CODE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN).details(UPDATED_DETAILS);
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(updatedAssurance);

        restAssuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assuranceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assuranceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
        Assurance testAssurance = assuranceList.get(assuranceList.size() - 1);
        assertThat(testAssurance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssurance.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAssurance.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAssurance.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingAssurance() throws Exception {
        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();
        assurance.setId(count.incrementAndGet());

        // Create the Assurance
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assuranceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssurance() throws Exception {
        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();
        assurance.setId(count.incrementAndGet());

        // Create the Assurance
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssurance() throws Exception {
        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();
        assurance.setId(count.incrementAndGet());

        // Create the Assurance
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssuranceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assuranceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssuranceWithPatch() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();

        // Update the assurance using partial update
        Assurance partialUpdatedAssurance = new Assurance();
        partialUpdatedAssurance.setId(assurance.getId());

        partialUpdatedAssurance.dateDebut(UPDATED_DATE_DEBUT).details(UPDATED_DETAILS);

        restAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssurance))
            )
            .andExpect(status().isOk());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
        Assurance testAssurance = assuranceList.get(assuranceList.size() - 1);
        assertThat(testAssurance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAssurance.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAssurance.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAssurance.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdateAssuranceWithPatch() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();

        // Update the assurance using partial update
        Assurance partialUpdatedAssurance = new Assurance();
        partialUpdatedAssurance.setId(assurance.getId());

        partialUpdatedAssurance.code(UPDATED_CODE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN).details(UPDATED_DETAILS);

        restAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssurance))
            )
            .andExpect(status().isOk());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
        Assurance testAssurance = assuranceList.get(assuranceList.size() - 1);
        assertThat(testAssurance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAssurance.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAssurance.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAssurance.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingAssurance() throws Exception {
        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();
        assurance.setId(count.incrementAndGet());

        // Create the Assurance
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assuranceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssurance() throws Exception {
        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();
        assurance.setId(count.incrementAndGet());

        // Create the Assurance
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssurance() throws Exception {
        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();
        assurance.setId(count.incrementAndGet());

        // Create the Assurance
        AssuranceDTO assuranceDTO = assuranceMapper.toDto(assurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assuranceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssurance() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        int databaseSizeBeforeDelete = assuranceRepository.findAll().size();

        // Delete the assurance
        restAssuranceMockMvc
            .perform(delete(ENTITY_API_URL_ID, assurance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
