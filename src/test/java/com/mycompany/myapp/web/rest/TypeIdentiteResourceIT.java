package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TypeIdentite;
import com.mycompany.myapp.repository.TypeIdentiteRepository;
import com.mycompany.myapp.service.dto.TypeIdentiteDTO;
import com.mycompany.myapp.service.mapper.TypeIdentiteMapper;
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
 * Integration tests for the {@link TypeIdentiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeIdentiteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/type-identites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeIdentiteRepository typeIdentiteRepository;

    @Autowired
    private TypeIdentiteMapper typeIdentiteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeIdentiteMockMvc;

    private TypeIdentite typeIdentite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeIdentite createEntity(EntityManager em) {
        TypeIdentite typeIdentite = new TypeIdentite()
            .code(DEFAULT_CODE)
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return typeIdentite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeIdentite createUpdatedEntity(EntityManager em) {
        TypeIdentite typeIdentite = new TypeIdentite()
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        return typeIdentite;
    }

    @BeforeEach
    public void initTest() {
        typeIdentite = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeIdentite() throws Exception {
        int databaseSizeBeforeCreate = typeIdentiteRepository.findAll().size();
        // Create the TypeIdentite
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);
        restTypeIdentiteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeIdentite.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypeIdentite.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTypeIdentite.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testTypeIdentite.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createTypeIdentiteWithExistingId() throws Exception {
        // Create the TypeIdentite with an existing ID
        typeIdentite.setId(1L);
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        int databaseSizeBeforeCreate = typeIdentiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeIdentiteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeIdentiteRepository.findAll().size();
        // set the field null
        typeIdentite.setCode(null);

        // Create the TypeIdentite, which fails.
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        restTypeIdentiteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeIdentiteRepository.findAll().size();
        // set the field null
        typeIdentite.setNom(null);

        // Create the TypeIdentite, which fails.
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        restTypeIdentiteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeIdentites() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        // Get all the typeIdentiteList
        restTypeIdentiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeIdentite.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    void getTypeIdentite() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        // Get the typeIdentite
        restTypeIdentiteMockMvc
            .perform(get(ENTITY_API_URL_ID, typeIdentite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeIdentite.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    void getNonExistingTypeIdentite() throws Exception {
        // Get the typeIdentite
        restTypeIdentiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeIdentite() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();

        // Update the typeIdentite
        TypeIdentite updatedTypeIdentite = typeIdentiteRepository.findById(typeIdentite.getId()).get();
        // Disconnect from session so that the updates on updatedTypeIdentite are not directly saved in db
        em.detach(updatedTypeIdentite);
        updatedTypeIdentite
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(updatedTypeIdentite);

        restTypeIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeIdentiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeIdentite.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeIdentite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypeIdentite.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testTypeIdentite.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // Create the TypeIdentite
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeIdentiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // Create the TypeIdentite
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // Create the TypeIdentite
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeIdentiteWithPatch() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();

        // Update the typeIdentite using partial update
        TypeIdentite partialUpdatedTypeIdentite = new TypeIdentite();
        partialUpdatedTypeIdentite.setId(typeIdentite.getId());

        partialUpdatedTypeIdentite.code(UPDATED_CODE).nom(UPDATED_NOM);

        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeIdentite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeIdentite))
            )
            .andExpect(status().isOk());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeIdentite.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeIdentite.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTypeIdentite.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testTypeIdentite.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTypeIdentiteWithPatch() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();

        // Update the typeIdentite using partial update
        TypeIdentite partialUpdatedTypeIdentite = new TypeIdentite();
        partialUpdatedTypeIdentite.setId(typeIdentite.getId());

        partialUpdatedTypeIdentite
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);

        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeIdentite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeIdentite))
            )
            .andExpect(status().isOk());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeIdentite.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeIdentite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypeIdentite.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testTypeIdentite.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // Create the TypeIdentite
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeIdentiteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // Create the TypeIdentite
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // Create the TypeIdentite
        TypeIdentiteDTO typeIdentiteDTO = typeIdentiteMapper.toDto(typeIdentite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeIdentite() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeDelete = typeIdentiteRepository.findAll().size();

        // Delete the typeIdentite
        restTypeIdentiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeIdentite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
