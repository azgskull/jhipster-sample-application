package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Salle;
import com.mycompany.myapp.repository.SalleRepository;
import com.mycompany.myapp.service.dto.SalleDTO;
import com.mycompany.myapp.service.mapper.SalleMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SalleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalleResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_WEB = "AAAAAAAAAA";
    private static final String UPDATED_SITE_WEB = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/salles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SalleMapper salleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalleMockMvc;

    private Salle salle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salle createEntity(EntityManager em) {
        Salle salle = new Salle()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .siteWeb(DEFAULT_SITE_WEB)
            .adresse(DEFAULT_ADRESSE)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE);
        return salle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salle createUpdatedEntity(EntityManager em) {
        Salle salle = new Salle()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .siteWeb(UPDATED_SITE_WEB)
            .adresse(UPDATED_ADRESSE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE);
        return salle;
    }

    @BeforeEach
    public void initTest() {
        salle = createEntity(em);
    }

    @Test
    @Transactional
    void createSalle() throws Exception {
        int databaseSizeBeforeCreate = salleRepository.findAll().size();
        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);
        restSalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isCreated());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate + 1);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSalle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSalle.getSiteWeb()).isEqualTo(DEFAULT_SITE_WEB);
        assertThat(testSalle.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testSalle.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testSalle.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testSalle.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSalle.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    void createSalleWithExistingId() throws Exception {
        // Create the Salle with an existing ID
        salle.setId(1L);
        SalleDTO salleDTO = salleMapper.toDto(salle);

        int databaseSizeBeforeCreate = salleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = salleRepository.findAll().size();
        // set the field null
        salle.setNom(null);

        // Create the Salle, which fails.
        SalleDTO salleDTO = salleMapper.toDto(salle);

        restSalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSalles() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList
        restSalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].siteWeb").value(hasItem(DEFAULT_SITE_WEB)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }

    @Test
    @Transactional
    void getSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get the salle
        restSalleMockMvc
            .perform(get(ENTITY_API_URL_ID, salle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salle.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.siteWeb").value(DEFAULT_SITE_WEB))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }

    @Test
    @Transactional
    void getNonExistingSalle() throws Exception {
        // Get the salle
        restSalleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        int databaseSizeBeforeUpdate = salleRepository.findAll().size();

        // Update the salle
        Salle updatedSalle = salleRepository.findById(salle.getId()).get();
        // Disconnect from session so that the updates on updatedSalle are not directly saved in db
        em.detach(updatedSalle);
        updatedSalle
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .siteWeb(UPDATED_SITE_WEB)
            .adresse(UPDATED_ADRESSE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE);
        SalleDTO salleDTO = salleMapper.toDto(updatedSalle);

        restSalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSalle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSalle.getSiteWeb()).isEqualTo(UPDATED_SITE_WEB);
        assertThat(testSalle.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSalle.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testSalle.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testSalle.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSalle.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void putNonExistingSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();
        salle.setId(count.incrementAndGet());

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();
        salle.setId(count.incrementAndGet());

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();
        salle.setId(count.incrementAndGet());

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalleWithPatch() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        int databaseSizeBeforeUpdate = salleRepository.findAll().size();

        // Update the salle using partial update
        Salle partialUpdatedSalle = new Salle();
        partialUpdatedSalle.setId(salle.getId());

        partialUpdatedSalle.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).adresse(UPDATED_ADRESSE).email(UPDATED_EMAIL);

        restSalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalle))
            )
            .andExpect(status().isOk());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSalle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSalle.getSiteWeb()).isEqualTo(DEFAULT_SITE_WEB);
        assertThat(testSalle.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSalle.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testSalle.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testSalle.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSalle.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    void fullUpdateSalleWithPatch() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        int databaseSizeBeforeUpdate = salleRepository.findAll().size();

        // Update the salle using partial update
        Salle partialUpdatedSalle = new Salle();
        partialUpdatedSalle.setId(salle.getId());

        partialUpdatedSalle
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .siteWeb(UPDATED_SITE_WEB)
            .adresse(UPDATED_ADRESSE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE);

        restSalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalle))
            )
            .andExpect(status().isOk());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSalle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSalle.getSiteWeb()).isEqualTo(UPDATED_SITE_WEB);
        assertThat(testSalle.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSalle.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testSalle.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testSalle.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSalle.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void patchNonExistingSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();
        salle.setId(count.incrementAndGet());

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();
        salle.setId(count.incrementAndGet());

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();
        salle.setId(count.incrementAndGet());

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        int databaseSizeBeforeDelete = salleRepository.findAll().size();

        // Delete the salle
        restSalleMockMvc
            .perform(delete(ENTITY_API_URL_ID, salle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
