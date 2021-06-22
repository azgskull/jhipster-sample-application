package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TypeCertificat;
import com.mycompany.myapp.repository.TypeCertificatRepository;
import com.mycompany.myapp.service.dto.TypeCertificatDTO;
import com.mycompany.myapp.service.mapper.TypeCertificatMapper;
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
 * Integration tests for the {@link TypeCertificatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeCertificatResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-certificats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeCertificatRepository typeCertificatRepository;

    @Autowired
    private TypeCertificatMapper typeCertificatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeCertificatMockMvc;

    private TypeCertificat typeCertificat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeCertificat createEntity(EntityManager em) {
        TypeCertificat typeCertificat = new TypeCertificat().code(DEFAULT_CODE).nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION);
        return typeCertificat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeCertificat createUpdatedEntity(EntityManager em) {
        TypeCertificat typeCertificat = new TypeCertificat().code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        return typeCertificat;
    }

    @BeforeEach
    public void initTest() {
        typeCertificat = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeCertificat() throws Exception {
        int databaseSizeBeforeCreate = typeCertificatRepository.findAll().size();
        // Create the TypeCertificat
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);
        restTypeCertificatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCertificat testTypeCertificat = typeCertificatList.get(typeCertificatList.size() - 1);
        assertThat(testTypeCertificat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeCertificat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypeCertificat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTypeCertificatWithExistingId() throws Exception {
        // Create the TypeCertificat with an existing ID
        typeCertificat.setId(1L);
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        int databaseSizeBeforeCreate = typeCertificatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCertificatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeCertificatRepository.findAll().size();
        // set the field null
        typeCertificat.setCode(null);

        // Create the TypeCertificat, which fails.
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        restTypeCertificatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeCertificatRepository.findAll().size();
        // set the field null
        typeCertificat.setNom(null);

        // Create the TypeCertificat, which fails.
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        restTypeCertificatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeCertificats() throws Exception {
        // Initialize the database
        typeCertificatRepository.saveAndFlush(typeCertificat);

        // Get all the typeCertificatList
        restTypeCertificatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCertificat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTypeCertificat() throws Exception {
        // Initialize the database
        typeCertificatRepository.saveAndFlush(typeCertificat);

        // Get the typeCertificat
        restTypeCertificatMockMvc
            .perform(get(ENTITY_API_URL_ID, typeCertificat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeCertificat.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTypeCertificat() throws Exception {
        // Get the typeCertificat
        restTypeCertificatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeCertificat() throws Exception {
        // Initialize the database
        typeCertificatRepository.saveAndFlush(typeCertificat);

        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();

        // Update the typeCertificat
        TypeCertificat updatedTypeCertificat = typeCertificatRepository.findById(typeCertificat.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCertificat are not directly saved in db
        em.detach(updatedTypeCertificat);
        updatedTypeCertificat.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(updatedTypeCertificat);

        restTypeCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeCertificatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
        TypeCertificat testTypeCertificat = typeCertificatList.get(typeCertificatList.size() - 1);
        assertThat(testTypeCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeCertificat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTypeCertificat() throws Exception {
        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();
        typeCertificat.setId(count.incrementAndGet());

        // Create the TypeCertificat
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeCertificatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeCertificat() throws Exception {
        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();
        typeCertificat.setId(count.incrementAndGet());

        // Create the TypeCertificat
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeCertificat() throws Exception {
        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();
        typeCertificat.setId(count.incrementAndGet());

        // Create the TypeCertificat
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeCertificatMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeCertificatWithPatch() throws Exception {
        // Initialize the database
        typeCertificatRepository.saveAndFlush(typeCertificat);

        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();

        // Update the typeCertificat using partial update
        TypeCertificat partialUpdatedTypeCertificat = new TypeCertificat();
        partialUpdatedTypeCertificat.setId(typeCertificat.getId());

        partialUpdatedTypeCertificat.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restTypeCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeCertificat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeCertificat))
            )
            .andExpect(status().isOk());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
        TypeCertificat testTypeCertificat = typeCertificatList.get(typeCertificatList.size() - 1);
        assertThat(testTypeCertificat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeCertificat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTypeCertificatWithPatch() throws Exception {
        // Initialize the database
        typeCertificatRepository.saveAndFlush(typeCertificat);

        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();

        // Update the typeCertificat using partial update
        TypeCertificat partialUpdatedTypeCertificat = new TypeCertificat();
        partialUpdatedTypeCertificat.setId(typeCertificat.getId());

        partialUpdatedTypeCertificat.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restTypeCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeCertificat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeCertificat))
            )
            .andExpect(status().isOk());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
        TypeCertificat testTypeCertificat = typeCertificatList.get(typeCertificatList.size() - 1);
        assertThat(testTypeCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeCertificat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTypeCertificat() throws Exception {
        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();
        typeCertificat.setId(count.incrementAndGet());

        // Create the TypeCertificat
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeCertificatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeCertificat() throws Exception {
        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();
        typeCertificat.setId(count.incrementAndGet());

        // Create the TypeCertificat
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeCertificat() throws Exception {
        int databaseSizeBeforeUpdate = typeCertificatRepository.findAll().size();
        typeCertificat.setId(count.incrementAndGet());

        // Create the TypeCertificat
        TypeCertificatDTO typeCertificatDTO = typeCertificatMapper.toDto(typeCertificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeCertificatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeCertificat in the database
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeCertificat() throws Exception {
        // Initialize the database
        typeCertificatRepository.saveAndFlush(typeCertificat);

        int databaseSizeBeforeDelete = typeCertificatRepository.findAll().size();

        // Delete the typeCertificat
        restTypeCertificatMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeCertificat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeCertificat> typeCertificatList = typeCertificatRepository.findAll();
        assertThat(typeCertificatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
