package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TemplateCertificat;
import com.mycompany.myapp.repository.TemplateCertificatRepository;
import com.mycompany.myapp.service.dto.TemplateCertificatDTO;
import com.mycompany.myapp.service.mapper.TemplateCertificatMapper;
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
 * Integration tests for the {@link TemplateCertificatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TemplateCertificatResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHIER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/template-certificats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplateCertificatRepository templateCertificatRepository;

    @Autowired
    private TemplateCertificatMapper templateCertificatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateCertificatMockMvc;

    private TemplateCertificat templateCertificat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateCertificat createEntity(EntityManager em) {
        TemplateCertificat templateCertificat = new TemplateCertificat()
            .code(DEFAULT_CODE)
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .fichier(DEFAULT_FICHIER)
            .fichierContentType(DEFAULT_FICHIER_CONTENT_TYPE);
        return templateCertificat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateCertificat createUpdatedEntity(EntityManager em) {
        TemplateCertificat templateCertificat = new TemplateCertificat()
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);
        return templateCertificat;
    }

    @BeforeEach
    public void initTest() {
        templateCertificat = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplateCertificat() throws Exception {
        int databaseSizeBeforeCreate = templateCertificatRepository.findAll().size();
        // Create the TemplateCertificat
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);
        restTemplateCertificatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateCertificat testTemplateCertificat = templateCertificatList.get(templateCertificatList.size() - 1);
        assertThat(testTemplateCertificat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTemplateCertificat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTemplateCertificat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTemplateCertificat.getFichier()).isEqualTo(DEFAULT_FICHIER);
        assertThat(testTemplateCertificat.getFichierContentType()).isEqualTo(DEFAULT_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createTemplateCertificatWithExistingId() throws Exception {
        // Create the TemplateCertificat with an existing ID
        templateCertificat.setId(1L);
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        int databaseSizeBeforeCreate = templateCertificatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateCertificatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateCertificatRepository.findAll().size();
        // set the field null
        templateCertificat.setCode(null);

        // Create the TemplateCertificat, which fails.
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        restTemplateCertificatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateCertificatRepository.findAll().size();
        // set the field null
        templateCertificat.setNom(null);

        // Create the TemplateCertificat, which fails.
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        restTemplateCertificatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTemplateCertificats() throws Exception {
        // Initialize the database
        templateCertificatRepository.saveAndFlush(templateCertificat);

        // Get all the templateCertificatList
        restTemplateCertificatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateCertificat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fichierContentType").value(hasItem(DEFAULT_FICHIER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER))));
    }

    @Test
    @Transactional
    void getTemplateCertificat() throws Exception {
        // Initialize the database
        templateCertificatRepository.saveAndFlush(templateCertificat);

        // Get the templateCertificat
        restTemplateCertificatMockMvc
            .perform(get(ENTITY_API_URL_ID, templateCertificat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateCertificat.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fichierContentType").value(DEFAULT_FICHIER_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier").value(Base64Utils.encodeToString(DEFAULT_FICHIER)));
    }

    @Test
    @Transactional
    void getNonExistingTemplateCertificat() throws Exception {
        // Get the templateCertificat
        restTemplateCertificatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTemplateCertificat() throws Exception {
        // Initialize the database
        templateCertificatRepository.saveAndFlush(templateCertificat);

        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();

        // Update the templateCertificat
        TemplateCertificat updatedTemplateCertificat = templateCertificatRepository.findById(templateCertificat.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateCertificat are not directly saved in db
        em.detach(updatedTemplateCertificat);
        updatedTemplateCertificat
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(updatedTemplateCertificat);

        restTemplateCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateCertificatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isOk());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
        TemplateCertificat testTemplateCertificat = templateCertificatList.get(templateCertificatList.size() - 1);
        assertThat(testTemplateCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateCertificat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTemplateCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTemplateCertificat.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testTemplateCertificat.getFichierContentType()).isEqualTo(UPDATED_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTemplateCertificat() throws Exception {
        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();
        templateCertificat.setId(count.incrementAndGet());

        // Create the TemplateCertificat
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateCertificatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplateCertificat() throws Exception {
        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();
        templateCertificat.setId(count.incrementAndGet());

        // Create the TemplateCertificat
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplateCertificat() throws Exception {
        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();
        templateCertificat.setId(count.incrementAndGet());

        // Create the TemplateCertificat
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCertificatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateCertificatWithPatch() throws Exception {
        // Initialize the database
        templateCertificatRepository.saveAndFlush(templateCertificat);

        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();

        // Update the templateCertificat using partial update
        TemplateCertificat partialUpdatedTemplateCertificat = new TemplateCertificat();
        partialUpdatedTemplateCertificat.setId(templateCertificat.getId());

        partialUpdatedTemplateCertificat.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTemplateCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateCertificat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateCertificat))
            )
            .andExpect(status().isOk());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
        TemplateCertificat testTemplateCertificat = templateCertificatList.get(templateCertificatList.size() - 1);
        assertThat(testTemplateCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateCertificat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTemplateCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTemplateCertificat.getFichier()).isEqualTo(DEFAULT_FICHIER);
        assertThat(testTemplateCertificat.getFichierContentType()).isEqualTo(DEFAULT_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTemplateCertificatWithPatch() throws Exception {
        // Initialize the database
        templateCertificatRepository.saveAndFlush(templateCertificat);

        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();

        // Update the templateCertificat using partial update
        TemplateCertificat partialUpdatedTemplateCertificat = new TemplateCertificat();
        partialUpdatedTemplateCertificat.setId(templateCertificat.getId());

        partialUpdatedTemplateCertificat
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);

        restTemplateCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateCertificat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateCertificat))
            )
            .andExpect(status().isOk());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
        TemplateCertificat testTemplateCertificat = templateCertificatList.get(templateCertificatList.size() - 1);
        assertThat(testTemplateCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateCertificat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTemplateCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTemplateCertificat.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testTemplateCertificat.getFichierContentType()).isEqualTo(UPDATED_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTemplateCertificat() throws Exception {
        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();
        templateCertificat.setId(count.incrementAndGet());

        // Create the TemplateCertificat
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateCertificatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplateCertificat() throws Exception {
        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();
        templateCertificat.setId(count.incrementAndGet());

        // Create the TemplateCertificat
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplateCertificat() throws Exception {
        int databaseSizeBeforeUpdate = templateCertificatRepository.findAll().size();
        templateCertificat.setId(count.incrementAndGet());

        // Create the TemplateCertificat
        TemplateCertificatDTO templateCertificatDTO = templateCertificatMapper.toDto(templateCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateCertificatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateCertificat in the database
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplateCertificat() throws Exception {
        // Initialize the database
        templateCertificatRepository.saveAndFlush(templateCertificat);

        int databaseSizeBeforeDelete = templateCertificatRepository.findAll().size();

        // Delete the templateCertificat
        restTemplateCertificatMockMvc
            .perform(delete(ENTITY_API_URL_ID, templateCertificat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateCertificat> templateCertificatList = templateCertificatRepository.findAll();
        assertThat(templateCertificatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
