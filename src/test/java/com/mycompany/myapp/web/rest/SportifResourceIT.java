package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Sportif;
import com.mycompany.myapp.repository.SportifRepository;
import com.mycompany.myapp.service.dto.SportifDTO;
import com.mycompany.myapp.service.mapper.SportifMapper;
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
 * Integration tests for the {@link SportifResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SportifResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_IDENTITE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IDENTITE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sportifs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SportifRepository sportifRepository;

    @Autowired
    private SportifMapper sportifMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportifMockMvc;

    private Sportif sportif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportif createEntity(EntityManager em) {
        Sportif sportif = new Sportif()
            .code(DEFAULT_CODE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .photo(DEFAULT_PHOTO)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .numeroIdentite(DEFAULT_NUMERO_IDENTITE)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL);
        return sportif;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sportif createUpdatedEntity(EntityManager em) {
        Sportif sportif = new Sportif()
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .numeroIdentite(UPDATED_NUMERO_IDENTITE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);
        return sportif;
    }

    @BeforeEach
    public void initTest() {
        sportif = createEntity(em);
    }

    @Test
    @Transactional
    void createSportif() throws Exception {
        int databaseSizeBeforeCreate = sportifRepository.findAll().size();
        // Create the Sportif
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);
        restSportifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportifDTO)))
            .andExpect(status().isCreated());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeCreate + 1);
        Sportif testSportif = sportifList.get(sportifList.size() - 1);
        assertThat(testSportif.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSportif.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSportif.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testSportif.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testSportif.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testSportif.getNumeroIdentite()).isEqualTo(DEFAULT_NUMERO_IDENTITE);
        assertThat(testSportif.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testSportif.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testSportif.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createSportifWithExistingId() throws Exception {
        // Create the Sportif with an existing ID
        sportif.setId(1L);
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        int databaseSizeBeforeCreate = sportifRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportifRepository.findAll().size();
        // set the field null
        sportif.setCode(null);

        // Create the Sportif, which fails.
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        restSportifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportifDTO)))
            .andExpect(status().isBadRequest());

        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportifRepository.findAll().size();
        // set the field null
        sportif.setNom(null);

        // Create the Sportif, which fails.
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        restSportifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportifDTO)))
            .andExpect(status().isBadRequest());

        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportifRepository.findAll().size();
        // set the field null
        sportif.setPrenom(null);

        // Create the Sportif, which fails.
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        restSportifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportifDTO)))
            .andExpect(status().isBadRequest());

        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSportifs() throws Exception {
        // Initialize the database
        sportifRepository.saveAndFlush(sportif);

        // Get all the sportifList
        restSportifMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sportif.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].numeroIdentite").value(hasItem(DEFAULT_NUMERO_IDENTITE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getSportif() throws Exception {
        // Initialize the database
        sportifRepository.saveAndFlush(sportif);

        // Get the sportif
        restSportifMockMvc
            .perform(get(ENTITY_API_URL_ID, sportif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sportif.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.numeroIdentite").value(DEFAULT_NUMERO_IDENTITE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingSportif() throws Exception {
        // Get the sportif
        restSportifMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSportif() throws Exception {
        // Initialize the database
        sportifRepository.saveAndFlush(sportif);

        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();

        // Update the sportif
        Sportif updatedSportif = sportifRepository.findById(sportif.getId()).get();
        // Disconnect from session so that the updates on updatedSportif are not directly saved in db
        em.detach(updatedSportif);
        updatedSportif
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .numeroIdentite(UPDATED_NUMERO_IDENTITE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);
        SportifDTO sportifDTO = sportifMapper.toDto(updatedSportif);

        restSportifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sportifDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sportifDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
        Sportif testSportif = sportifList.get(sportifList.size() - 1);
        assertThat(testSportif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSportif.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSportif.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSportif.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSportif.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testSportif.getNumeroIdentite()).isEqualTo(UPDATED_NUMERO_IDENTITE);
        assertThat(testSportif.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSportif.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSportif.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingSportif() throws Exception {
        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();
        sportif.setId(count.incrementAndGet());

        // Create the Sportif
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sportifDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sportifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSportif() throws Exception {
        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();
        sportif.setId(count.incrementAndGet());

        // Create the Sportif
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sportifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSportif() throws Exception {
        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();
        sportif.setId(count.incrementAndGet());

        // Create the Sportif
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportifMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sportifDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSportifWithPatch() throws Exception {
        // Initialize the database
        sportifRepository.saveAndFlush(sportif);

        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();

        // Update the sportif using partial update
        Sportif partialUpdatedSportif = new Sportif();
        partialUpdatedSportif.setId(sportif.getId());

        partialUpdatedSportif.email(UPDATED_EMAIL);

        restSportifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportif.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSportif))
            )
            .andExpect(status().isOk());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
        Sportif testSportif = sportifList.get(sportifList.size() - 1);
        assertThat(testSportif.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSportif.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSportif.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testSportif.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testSportif.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testSportif.getNumeroIdentite()).isEqualTo(DEFAULT_NUMERO_IDENTITE);
        assertThat(testSportif.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testSportif.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testSportif.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateSportifWithPatch() throws Exception {
        // Initialize the database
        sportifRepository.saveAndFlush(sportif);

        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();

        // Update the sportif using partial update
        Sportif partialUpdatedSportif = new Sportif();
        partialUpdatedSportif.setId(sportif.getId());

        partialUpdatedSportif
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .numeroIdentite(UPDATED_NUMERO_IDENTITE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);

        restSportifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSportif.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSportif))
            )
            .andExpect(status().isOk());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
        Sportif testSportif = sportifList.get(sportifList.size() - 1);
        assertThat(testSportif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSportif.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSportif.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSportif.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSportif.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testSportif.getNumeroIdentite()).isEqualTo(UPDATED_NUMERO_IDENTITE);
        assertThat(testSportif.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSportif.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSportif.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingSportif() throws Exception {
        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();
        sportif.setId(count.incrementAndGet());

        // Create the Sportif
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sportifDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sportifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSportif() throws Exception {
        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();
        sportif.setId(count.incrementAndGet());

        // Create the Sportif
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sportifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSportif() throws Exception {
        int databaseSizeBeforeUpdate = sportifRepository.findAll().size();
        sportif.setId(count.incrementAndGet());

        // Create the Sportif
        SportifDTO sportifDTO = sportifMapper.toDto(sportif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSportifMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sportifDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sportif in the database
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSportif() throws Exception {
        // Initialize the database
        sportifRepository.saveAndFlush(sportif);

        int databaseSizeBeforeDelete = sportifRepository.findAll().size();

        // Delete the sportif
        restSportifMockMvc
            .perform(delete(ENTITY_API_URL_ID, sportif.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sportif> sportifList = sportifRepository.findAll();
        assertThat(sportifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
