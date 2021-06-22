package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Echeance;
import com.mycompany.myapp.repository.EcheanceRepository;
import com.mycompany.myapp.service.EcheanceService;
import com.mycompany.myapp.service.dto.EcheanceDTO;
import com.mycompany.myapp.service.mapper.EcheanceMapper;
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
 * Integration tests for the {@link EcheanceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EcheanceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_PREVU_PAIEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PREVU_PAIEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Boolean DEFAULT_PAYE_TOTALEMENT = false;
    private static final Boolean UPDATED_PAYE_TOTALEMENT = true;

    private static final String ENTITY_API_URL = "/api/echeances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EcheanceRepository echeanceRepository;

    @Mock
    private EcheanceRepository echeanceRepositoryMock;

    @Autowired
    private EcheanceMapper echeanceMapper;

    @Mock
    private EcheanceService echeanceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcheanceMockMvc;

    private Echeance echeance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Echeance createEntity(EntityManager em) {
        Echeance echeance = new Echeance()
            .code(DEFAULT_CODE)
            .datePrevuPaiement(DEFAULT_DATE_PREVU_PAIEMENT)
            .montant(DEFAULT_MONTANT)
            .payeTotalement(DEFAULT_PAYE_TOTALEMENT);
        return echeance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Echeance createUpdatedEntity(EntityManager em) {
        Echeance echeance = new Echeance()
            .code(UPDATED_CODE)
            .datePrevuPaiement(UPDATED_DATE_PREVU_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .payeTotalement(UPDATED_PAYE_TOTALEMENT);
        return echeance;
    }

    @BeforeEach
    public void initTest() {
        echeance = createEntity(em);
    }

    @Test
    @Transactional
    void createEcheance() throws Exception {
        int databaseSizeBeforeCreate = echeanceRepository.findAll().size();
        // Create the Echeance
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);
        restEcheanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echeanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeCreate + 1);
        Echeance testEcheance = echeanceList.get(echeanceList.size() - 1);
        assertThat(testEcheance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEcheance.getDatePrevuPaiement()).isEqualTo(DEFAULT_DATE_PREVU_PAIEMENT);
        assertThat(testEcheance.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testEcheance.getPayeTotalement()).isEqualTo(DEFAULT_PAYE_TOTALEMENT);
    }

    @Test
    @Transactional
    void createEcheanceWithExistingId() throws Exception {
        // Create the Echeance with an existing ID
        echeance.setId(1L);
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        int databaseSizeBeforeCreate = echeanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcheanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echeanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = echeanceRepository.findAll().size();
        // set the field null
        echeance.setCode(null);

        // Create the Echeance, which fails.
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        restEcheanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echeanceDTO)))
            .andExpect(status().isBadRequest());

        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDatePrevuPaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = echeanceRepository.findAll().size();
        // set the field null
        echeance.setDatePrevuPaiement(null);

        // Create the Echeance, which fails.
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        restEcheanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echeanceDTO)))
            .andExpect(status().isBadRequest());

        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = echeanceRepository.findAll().size();
        // set the field null
        echeance.setMontant(null);

        // Create the Echeance, which fails.
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        restEcheanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echeanceDTO)))
            .andExpect(status().isBadRequest());

        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEcheances() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get all the echeanceList
        restEcheanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(echeance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].datePrevuPaiement").value(hasItem(DEFAULT_DATE_PREVU_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].payeTotalement").value(hasItem(DEFAULT_PAYE_TOTALEMENT.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcheancesWithEagerRelationshipsIsEnabled() throws Exception {
        when(echeanceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcheanceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(echeanceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEcheancesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(echeanceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEcheanceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(echeanceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEcheance() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        // Get the echeance
        restEcheanceMockMvc
            .perform(get(ENTITY_API_URL_ID, echeance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(echeance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.datePrevuPaiement").value(DEFAULT_DATE_PREVU_PAIEMENT.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.payeTotalement").value(DEFAULT_PAYE_TOTALEMENT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEcheance() throws Exception {
        // Get the echeance
        restEcheanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEcheance() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();

        // Update the echeance
        Echeance updatedEcheance = echeanceRepository.findById(echeance.getId()).get();
        // Disconnect from session so that the updates on updatedEcheance are not directly saved in db
        em.detach(updatedEcheance);
        updatedEcheance
            .code(UPDATED_CODE)
            .datePrevuPaiement(UPDATED_DATE_PREVU_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .payeTotalement(UPDATED_PAYE_TOTALEMENT);
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(updatedEcheance);

        restEcheanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, echeanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(echeanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
        Echeance testEcheance = echeanceList.get(echeanceList.size() - 1);
        assertThat(testEcheance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEcheance.getDatePrevuPaiement()).isEqualTo(UPDATED_DATE_PREVU_PAIEMENT);
        assertThat(testEcheance.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testEcheance.getPayeTotalement()).isEqualTo(UPDATED_PAYE_TOTALEMENT);
    }

    @Test
    @Transactional
    void putNonExistingEcheance() throws Exception {
        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();
        echeance.setId(count.incrementAndGet());

        // Create the Echeance
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcheanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, echeanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(echeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEcheance() throws Exception {
        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();
        echeance.setId(count.incrementAndGet());

        // Create the Echeance
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcheanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(echeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEcheance() throws Exception {
        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();
        echeance.setId(count.incrementAndGet());

        // Create the Echeance
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcheanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echeanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEcheanceWithPatch() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();

        // Update the echeance using partial update
        Echeance partialUpdatedEcheance = new Echeance();
        partialUpdatedEcheance.setId(echeance.getId());

        partialUpdatedEcheance
            .code(UPDATED_CODE)
            .datePrevuPaiement(UPDATED_DATE_PREVU_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .payeTotalement(UPDATED_PAYE_TOTALEMENT);

        restEcheanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcheance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcheance))
            )
            .andExpect(status().isOk());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
        Echeance testEcheance = echeanceList.get(echeanceList.size() - 1);
        assertThat(testEcheance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEcheance.getDatePrevuPaiement()).isEqualTo(UPDATED_DATE_PREVU_PAIEMENT);
        assertThat(testEcheance.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testEcheance.getPayeTotalement()).isEqualTo(UPDATED_PAYE_TOTALEMENT);
    }

    @Test
    @Transactional
    void fullUpdateEcheanceWithPatch() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();

        // Update the echeance using partial update
        Echeance partialUpdatedEcheance = new Echeance();
        partialUpdatedEcheance.setId(echeance.getId());

        partialUpdatedEcheance
            .code(UPDATED_CODE)
            .datePrevuPaiement(UPDATED_DATE_PREVU_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .payeTotalement(UPDATED_PAYE_TOTALEMENT);

        restEcheanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEcheance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEcheance))
            )
            .andExpect(status().isOk());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
        Echeance testEcheance = echeanceList.get(echeanceList.size() - 1);
        assertThat(testEcheance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEcheance.getDatePrevuPaiement()).isEqualTo(UPDATED_DATE_PREVU_PAIEMENT);
        assertThat(testEcheance.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testEcheance.getPayeTotalement()).isEqualTo(UPDATED_PAYE_TOTALEMENT);
    }

    @Test
    @Transactional
    void patchNonExistingEcheance() throws Exception {
        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();
        echeance.setId(count.incrementAndGet());

        // Create the Echeance
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcheanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, echeanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(echeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEcheance() throws Exception {
        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();
        echeance.setId(count.incrementAndGet());

        // Create the Echeance
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcheanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(echeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEcheance() throws Exception {
        int databaseSizeBeforeUpdate = echeanceRepository.findAll().size();
        echeance.setId(count.incrementAndGet());

        // Create the Echeance
        EcheanceDTO echeanceDTO = echeanceMapper.toDto(echeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEcheanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(echeanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Echeance in the database
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEcheance() throws Exception {
        // Initialize the database
        echeanceRepository.saveAndFlush(echeance);

        int databaseSizeBeforeDelete = echeanceRepository.findAll().size();

        // Delete the echeance
        restEcheanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, echeance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Echeance> echeanceList = echeanceRepository.findAll();
        assertThat(echeanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
