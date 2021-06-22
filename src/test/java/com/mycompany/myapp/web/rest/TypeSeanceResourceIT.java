package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TypeSeance;
import com.mycompany.myapp.repository.TypeSeanceRepository;
import com.mycompany.myapp.service.dto.TypeSeanceDTO;
import com.mycompany.myapp.service.mapper.TypeSeanceMapper;
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
 * Integration tests for the {@link TypeSeanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeSeanceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-seances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeSeanceRepository typeSeanceRepository;

    @Autowired
    private TypeSeanceMapper typeSeanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeSeanceMockMvc;

    private TypeSeance typeSeance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeSeance createEntity(EntityManager em) {
        TypeSeance typeSeance = new TypeSeance().code(DEFAULT_CODE).nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION);
        return typeSeance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeSeance createUpdatedEntity(EntityManager em) {
        TypeSeance typeSeance = new TypeSeance().code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        return typeSeance;
    }

    @BeforeEach
    public void initTest() {
        typeSeance = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeSeance() throws Exception {
        int databaseSizeBeforeCreate = typeSeanceRepository.findAll().size();
        // Create the TypeSeance
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);
        restTypeSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeCreate + 1);
        TypeSeance testTypeSeance = typeSeanceList.get(typeSeanceList.size() - 1);
        assertThat(testTypeSeance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeSeance.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTypeSeance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTypeSeanceWithExistingId() throws Exception {
        // Create the TypeSeance with an existing ID
        typeSeance.setId(1L);
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        int databaseSizeBeforeCreate = typeSeanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeSeanceRepository.findAll().size();
        // set the field null
        typeSeance.setCode(null);

        // Create the TypeSeance, which fails.
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        restTypeSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO)))
            .andExpect(status().isBadRequest());

        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeSeanceRepository.findAll().size();
        // set the field null
        typeSeance.setNom(null);

        // Create the TypeSeance, which fails.
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        restTypeSeanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO)))
            .andExpect(status().isBadRequest());

        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeSeances() throws Exception {
        // Initialize the database
        typeSeanceRepository.saveAndFlush(typeSeance);

        // Get all the typeSeanceList
        restTypeSeanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeSeance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTypeSeance() throws Exception {
        // Initialize the database
        typeSeanceRepository.saveAndFlush(typeSeance);

        // Get the typeSeance
        restTypeSeanceMockMvc
            .perform(get(ENTITY_API_URL_ID, typeSeance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeSeance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTypeSeance() throws Exception {
        // Get the typeSeance
        restTypeSeanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeSeance() throws Exception {
        // Initialize the database
        typeSeanceRepository.saveAndFlush(typeSeance);

        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();

        // Update the typeSeance
        TypeSeance updatedTypeSeance = typeSeanceRepository.findById(typeSeance.getId()).get();
        // Disconnect from session so that the updates on updatedTypeSeance are not directly saved in db
        em.detach(updatedTypeSeance);
        updatedTypeSeance.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(updatedTypeSeance);

        restTypeSeanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeSeanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
        TypeSeance testTypeSeance = typeSeanceList.get(typeSeanceList.size() - 1);
        assertThat(testTypeSeance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeSeance.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeSeance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTypeSeance() throws Exception {
        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();
        typeSeance.setId(count.incrementAndGet());

        // Create the TypeSeance
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeSeanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeSeanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeSeance() throws Exception {
        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();
        typeSeance.setId(count.incrementAndGet());

        // Create the TypeSeance
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeSeanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeSeance() throws Exception {
        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();
        typeSeance.setId(count.incrementAndGet());

        // Create the TypeSeance
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeSeanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeSeanceWithPatch() throws Exception {
        // Initialize the database
        typeSeanceRepository.saveAndFlush(typeSeance);

        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();

        // Update the typeSeance using partial update
        TypeSeance partialUpdatedTypeSeance = new TypeSeance();
        partialUpdatedTypeSeance.setId(typeSeance.getId());

        partialUpdatedTypeSeance.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restTypeSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeSeance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeSeance))
            )
            .andExpect(status().isOk());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
        TypeSeance testTypeSeance = typeSeanceList.get(typeSeanceList.size() - 1);
        assertThat(testTypeSeance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeSeance.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeSeance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTypeSeanceWithPatch() throws Exception {
        // Initialize the database
        typeSeanceRepository.saveAndFlush(typeSeance);

        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();

        // Update the typeSeance using partial update
        TypeSeance partialUpdatedTypeSeance = new TypeSeance();
        partialUpdatedTypeSeance.setId(typeSeance.getId());

        partialUpdatedTypeSeance.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION);

        restTypeSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeSeance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeSeance))
            )
            .andExpect(status().isOk());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
        TypeSeance testTypeSeance = typeSeanceList.get(typeSeanceList.size() - 1);
        assertThat(testTypeSeance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeSeance.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTypeSeance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTypeSeance() throws Exception {
        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();
        typeSeance.setId(count.incrementAndGet());

        // Create the TypeSeance
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeSeanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeSeance() throws Exception {
        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();
        typeSeance.setId(count.incrementAndGet());

        // Create the TypeSeance
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeSeance() throws Exception {
        int databaseSizeBeforeUpdate = typeSeanceRepository.findAll().size();
        typeSeance.setId(count.incrementAndGet());

        // Create the TypeSeance
        TypeSeanceDTO typeSeanceDTO = typeSeanceMapper.toDto(typeSeance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeSeanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeSeanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeSeance in the database
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeSeance() throws Exception {
        // Initialize the database
        typeSeanceRepository.saveAndFlush(typeSeance);

        int databaseSizeBeforeDelete = typeSeanceRepository.findAll().size();

        // Delete the typeSeance
        restTypeSeanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeSeance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeSeance> typeSeanceList = typeSeanceRepository.findAll();
        assertThat(typeSeanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
