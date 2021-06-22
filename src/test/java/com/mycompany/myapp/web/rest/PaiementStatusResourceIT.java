package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PaiementStatus;
import com.mycompany.myapp.repository.PaiementStatusRepository;
import com.mycompany.myapp.service.dto.PaiementStatusDTO;
import com.mycompany.myapp.service.mapper.PaiementStatusMapper;
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
 * Integration tests for the {@link PaiementStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaiementStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paiement-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaiementStatusRepository paiementStatusRepository;

    @Autowired
    private PaiementStatusMapper paiementStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaiementStatusMockMvc;

    private PaiementStatus paiementStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaiementStatus createEntity(EntityManager em) {
        PaiementStatus paiementStatus = new PaiementStatus().code(DEFAULT_CODE).nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION);
        return paiementStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaiementStatus createUpdatedEntity(EntityManager em) {
        PaiementStatus paiementStatus = new PaiementStatus().code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        return paiementStatus;
    }

    @BeforeEach
    public void initTest() {
        paiementStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createPaiementStatus() throws Exception {
        int databaseSizeBeforeCreate = paiementStatusRepository.findAll().size();
        // Create the PaiementStatus
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);
        restPaiementStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PaiementStatus testPaiementStatus = paiementStatusList.get(paiementStatusList.size() - 1);
        assertThat(testPaiementStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPaiementStatus.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPaiementStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPaiementStatusWithExistingId() throws Exception {
        // Create the PaiementStatus with an existing ID
        paiementStatus.setId(1L);
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        int databaseSizeBeforeCreate = paiementStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementStatusRepository.findAll().size();
        // set the field null
        paiementStatus.setCode(null);

        // Create the PaiementStatus, which fails.
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        restPaiementStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementStatusRepository.findAll().size();
        // set the field null
        paiementStatus.setNom(null);

        // Create the PaiementStatus, which fails.
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        restPaiementStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaiementStatuses() throws Exception {
        // Initialize the database
        paiementStatusRepository.saveAndFlush(paiementStatus);

        // Get all the paiementStatusList
        restPaiementStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiementStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPaiementStatus() throws Exception {
        // Initialize the database
        paiementStatusRepository.saveAndFlush(paiementStatus);

        // Get the paiementStatus
        restPaiementStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, paiementStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paiementStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingPaiementStatus() throws Exception {
        // Get the paiementStatus
        restPaiementStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaiementStatus() throws Exception {
        // Initialize the database
        paiementStatusRepository.saveAndFlush(paiementStatus);

        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();

        // Update the paiementStatus
        PaiementStatus updatedPaiementStatus = paiementStatusRepository.findById(paiementStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPaiementStatus are not directly saved in db
        em.detach(updatedPaiementStatus);
        updatedPaiementStatus.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(updatedPaiementStatus);

        restPaiementStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paiementStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
        PaiementStatus testPaiementStatus = paiementStatusList.get(paiementStatusList.size() - 1);
        assertThat(testPaiementStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPaiementStatus.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPaiementStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPaiementStatus() throws Exception {
        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();
        paiementStatus.setId(count.incrementAndGet());

        // Create the PaiementStatus
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paiementStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaiementStatus() throws Exception {
        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();
        paiementStatus.setId(count.incrementAndGet());

        // Create the PaiementStatus
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaiementStatus() throws Exception {
        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();
        paiementStatus.setId(count.incrementAndGet());

        // Create the PaiementStatus
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaiementStatusWithPatch() throws Exception {
        // Initialize the database
        paiementStatusRepository.saveAndFlush(paiementStatus);

        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();

        // Update the paiementStatus using partial update
        PaiementStatus partialUpdatedPaiementStatus = new PaiementStatus();
        partialUpdatedPaiementStatus.setId(paiementStatus.getId());

        restPaiementStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaiementStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaiementStatus))
            )
            .andExpect(status().isOk());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
        PaiementStatus testPaiementStatus = paiementStatusList.get(paiementStatusList.size() - 1);
        assertThat(testPaiementStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPaiementStatus.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPaiementStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePaiementStatusWithPatch() throws Exception {
        // Initialize the database
        paiementStatusRepository.saveAndFlush(paiementStatus);

        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();

        // Update the paiementStatus using partial update
        PaiementStatus partialUpdatedPaiementStatus = new PaiementStatus();
        partialUpdatedPaiementStatus.setId(paiementStatus.getId());

        partialUpdatedPaiementStatus.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restPaiementStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaiementStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaiementStatus))
            )
            .andExpect(status().isOk());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
        PaiementStatus testPaiementStatus = paiementStatusList.get(paiementStatusList.size() - 1);
        assertThat(testPaiementStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPaiementStatus.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPaiementStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPaiementStatus() throws Exception {
        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();
        paiementStatus.setId(count.incrementAndGet());

        // Create the PaiementStatus
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paiementStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaiementStatus() throws Exception {
        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();
        paiementStatus.setId(count.incrementAndGet());

        // Create the PaiementStatus
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaiementStatus() throws Exception {
        int databaseSizeBeforeUpdate = paiementStatusRepository.findAll().size();
        paiementStatus.setId(count.incrementAndGet());

        // Create the PaiementStatus
        PaiementStatusDTO paiementStatusDTO = paiementStatusMapper.toDto(paiementStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paiementStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaiementStatus in the database
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaiementStatus() throws Exception {
        // Initialize the database
        paiementStatusRepository.saveAndFlush(paiementStatus);

        int databaseSizeBeforeDelete = paiementStatusRepository.findAll().size();

        // Delete the paiementStatus
        restPaiementStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, paiementStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaiementStatus> paiementStatusList = paiementStatusRepository.findAll();
        assertThat(paiementStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
