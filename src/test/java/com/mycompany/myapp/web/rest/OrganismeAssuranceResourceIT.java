package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.OrganismeAssurance;
import com.mycompany.myapp.repository.OrganismeAssuranceRepository;
import com.mycompany.myapp.service.dto.OrganismeAssuranceDTO;
import com.mycompany.myapp.service.mapper.OrganismeAssuranceMapper;
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
 * Integration tests for the {@link OrganismeAssuranceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganismeAssuranceResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organisme-assurances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganismeAssuranceRepository organismeAssuranceRepository;

    @Autowired
    private OrganismeAssuranceMapper organismeAssuranceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganismeAssuranceMockMvc;

    private OrganismeAssurance organismeAssurance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganismeAssurance createEntity(EntityManager em) {
        OrganismeAssurance organismeAssurance = new OrganismeAssurance().nom(DEFAULT_NOM);
        return organismeAssurance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganismeAssurance createUpdatedEntity(EntityManager em) {
        OrganismeAssurance organismeAssurance = new OrganismeAssurance().nom(UPDATED_NOM);
        return organismeAssurance;
    }

    @BeforeEach
    public void initTest() {
        organismeAssurance = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganismeAssurance() throws Exception {
        int databaseSizeBeforeCreate = organismeAssuranceRepository.findAll().size();
        // Create the OrganismeAssurance
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);
        restOrganismeAssuranceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeCreate + 1);
        OrganismeAssurance testOrganismeAssurance = organismeAssuranceList.get(organismeAssuranceList.size() - 1);
        assertThat(testOrganismeAssurance.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void createOrganismeAssuranceWithExistingId() throws Exception {
        // Create the OrganismeAssurance with an existing ID
        organismeAssurance.setId(1L);
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        int databaseSizeBeforeCreate = organismeAssuranceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganismeAssuranceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = organismeAssuranceRepository.findAll().size();
        // set the field null
        organismeAssurance.setNom(null);

        // Create the OrganismeAssurance, which fails.
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        restOrganismeAssuranceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganismeAssurances() throws Exception {
        // Initialize the database
        organismeAssuranceRepository.saveAndFlush(organismeAssurance);

        // Get all the organismeAssuranceList
        restOrganismeAssuranceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organismeAssurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    void getOrganismeAssurance() throws Exception {
        // Initialize the database
        organismeAssuranceRepository.saveAndFlush(organismeAssurance);

        // Get the organismeAssurance
        restOrganismeAssuranceMockMvc
            .perform(get(ENTITY_API_URL_ID, organismeAssurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organismeAssurance.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingOrganismeAssurance() throws Exception {
        // Get the organismeAssurance
        restOrganismeAssuranceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrganismeAssurance() throws Exception {
        // Initialize the database
        organismeAssuranceRepository.saveAndFlush(organismeAssurance);

        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();

        // Update the organismeAssurance
        OrganismeAssurance updatedOrganismeAssurance = organismeAssuranceRepository.findById(organismeAssurance.getId()).get();
        // Disconnect from session so that the updates on updatedOrganismeAssurance are not directly saved in db
        em.detach(updatedOrganismeAssurance);
        updatedOrganismeAssurance.nom(UPDATED_NOM);
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(updatedOrganismeAssurance);

        restOrganismeAssuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organismeAssuranceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
        OrganismeAssurance testOrganismeAssurance = organismeAssuranceList.get(organismeAssuranceList.size() - 1);
        assertThat(testOrganismeAssurance.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void putNonExistingOrganismeAssurance() throws Exception {
        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();
        organismeAssurance.setId(count.incrementAndGet());

        // Create the OrganismeAssurance
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganismeAssuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organismeAssuranceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganismeAssurance() throws Exception {
        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();
        organismeAssurance.setId(count.incrementAndGet());

        // Create the OrganismeAssurance
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismeAssuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganismeAssurance() throws Exception {
        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();
        organismeAssurance.setId(count.incrementAndGet());

        // Create the OrganismeAssurance
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismeAssuranceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganismeAssuranceWithPatch() throws Exception {
        // Initialize the database
        organismeAssuranceRepository.saveAndFlush(organismeAssurance);

        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();

        // Update the organismeAssurance using partial update
        OrganismeAssurance partialUpdatedOrganismeAssurance = new OrganismeAssurance();
        partialUpdatedOrganismeAssurance.setId(organismeAssurance.getId());

        partialUpdatedOrganismeAssurance.nom(UPDATED_NOM);

        restOrganismeAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganismeAssurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganismeAssurance))
            )
            .andExpect(status().isOk());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
        OrganismeAssurance testOrganismeAssurance = organismeAssuranceList.get(organismeAssuranceList.size() - 1);
        assertThat(testOrganismeAssurance.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void fullUpdateOrganismeAssuranceWithPatch() throws Exception {
        // Initialize the database
        organismeAssuranceRepository.saveAndFlush(organismeAssurance);

        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();

        // Update the organismeAssurance using partial update
        OrganismeAssurance partialUpdatedOrganismeAssurance = new OrganismeAssurance();
        partialUpdatedOrganismeAssurance.setId(organismeAssurance.getId());

        partialUpdatedOrganismeAssurance.nom(UPDATED_NOM);

        restOrganismeAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganismeAssurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganismeAssurance))
            )
            .andExpect(status().isOk());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
        OrganismeAssurance testOrganismeAssurance = organismeAssuranceList.get(organismeAssuranceList.size() - 1);
        assertThat(testOrganismeAssurance.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void patchNonExistingOrganismeAssurance() throws Exception {
        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();
        organismeAssurance.setId(count.incrementAndGet());

        // Create the OrganismeAssurance
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganismeAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organismeAssuranceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganismeAssurance() throws Exception {
        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();
        organismeAssurance.setId(count.incrementAndGet());

        // Create the OrganismeAssurance
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismeAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganismeAssurance() throws Exception {
        int databaseSizeBeforeUpdate = organismeAssuranceRepository.findAll().size();
        organismeAssurance.setId(count.incrementAndGet());

        // Create the OrganismeAssurance
        OrganismeAssuranceDTO organismeAssuranceDTO = organismeAssuranceMapper.toDto(organismeAssurance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismeAssuranceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organismeAssuranceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganismeAssurance in the database
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganismeAssurance() throws Exception {
        // Initialize the database
        organismeAssuranceRepository.saveAndFlush(organismeAssurance);

        int databaseSizeBeforeDelete = organismeAssuranceRepository.findAll().size();

        // Delete the organismeAssurance
        restOrganismeAssuranceMockMvc
            .perform(delete(ENTITY_API_URL_ID, organismeAssurance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganismeAssurance> organismeAssuranceList = organismeAssuranceRepository.findAll();
        assertThat(organismeAssuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
