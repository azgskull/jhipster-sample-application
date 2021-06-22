package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Employe;
import com.mycompany.myapp.repository.EmployeRepository;
import com.mycompany.myapp.service.dto.EmployeDTO;
import com.mycompany.myapp.service.mapper.EmployeMapper;
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
 * Integration tests for the {@link EmployeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/employes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeMapper employeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeMockMvc;

    private Employe employe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createEntity(EntityManager em) {
        Employe employe = new Employe()
            .code(DEFAULT_CODE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .photo(DEFAULT_PHOTO)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .numeroIdentite(DEFAULT_NUMERO_IDENTITE)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL);
        return employe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createUpdatedEntity(EntityManager em) {
        Employe employe = new Employe()
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .numeroIdentite(UPDATED_NUMERO_IDENTITE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);
        return employe;
    }

    @BeforeEach
    public void initTest() {
        employe = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploye() throws Exception {
        int databaseSizeBeforeCreate = employeRepository.findAll().size();
        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);
        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate + 1);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmploye.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEmploye.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmploye.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEmploye.getNumeroIdentite()).isEqualTo(DEFAULT_NUMERO_IDENTITE);
        assertThat(testEmploye.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEmploye.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createEmployeWithExistingId() throws Exception {
        // Create the Employe with an existing ID
        employe.setId(1L);
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        int databaseSizeBeforeCreate = employeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setCode(null);

        // Create the Employe, which fails.
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setNom(null);

        // Create the Employe, which fails.
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setPrenom(null);

        // Create the Employe, which fails.
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployes() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
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
    void getEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get the employe
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL_ID, employe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employe.getId().intValue()))
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
    void getNonExistingEmploye() throws Exception {
        // Get the employe
        restEmployeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe
        Employe updatedEmploye = employeRepository.findById(employe.getId()).get();
        // Disconnect from session so that the updates on updatedEmploye are not directly saved in db
        em.detach(updatedEmploye);
        updatedEmploye
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .numeroIdentite(UPDATED_NUMERO_IDENTITE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);
        EmployeDTO employeDTO = employeMapper.toDto(updatedEmploye);

        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmploye.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEmploye.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmploye.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEmploye.getNumeroIdentite()).isEqualTo(UPDATED_NUMERO_IDENTITE);
        assertThat(testEmploye.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmploye.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).adresse(UPDATED_ADRESSE);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmploye.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEmploye.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmploye.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEmploye.getNumeroIdentite()).isEqualTo(DEFAULT_NUMERO_IDENTITE);
        assertThat(testEmploye.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmploye.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .photo(UPDATED_PHOTO)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .numeroIdentite(UPDATED_NUMERO_IDENTITE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmploye.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEmploye.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmploye.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEmploye.getNumeroIdentite()).isEqualTo(UPDATED_NUMERO_IDENTITE);
        assertThat(testEmploye.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmploye.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeDelete = employeRepository.findAll().size();

        // Delete the employe
        restEmployeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
