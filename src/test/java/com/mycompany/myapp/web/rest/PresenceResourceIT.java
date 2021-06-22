package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Presence;
import com.mycompany.myapp.repository.PresenceRepository;
import com.mycompany.myapp.service.dto.PresenceDTO;
import com.mycompany.myapp.service.mapper.PresenceMapper;
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
 * Integration tests for the {@link PresenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PresenceResourceIT {

    private static final String DEFAULT_HEURE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_FIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/presences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private PresenceMapper presenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPresenceMockMvc;

    private Presence presence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Presence createEntity(EntityManager em) {
        Presence presence = new Presence().heureDebut(DEFAULT_HEURE_DEBUT).heureFin(DEFAULT_HEURE_FIN).description(DEFAULT_DESCRIPTION);
        return presence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Presence createUpdatedEntity(EntityManager em) {
        Presence presence = new Presence().heureDebut(UPDATED_HEURE_DEBUT).heureFin(UPDATED_HEURE_FIN).description(UPDATED_DESCRIPTION);
        return presence;
    }

    @BeforeEach
    public void initTest() {
        presence = createEntity(em);
    }

    @Test
    @Transactional
    void createPresence() throws Exception {
        int databaseSizeBeforeCreate = presenceRepository.findAll().size();
        // Create the Presence
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);
        restPresenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(presenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeCreate + 1);
        Presence testPresence = presenceList.get(presenceList.size() - 1);
        assertThat(testPresence.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testPresence.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
        assertThat(testPresence.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPresenceWithExistingId() throws Exception {
        // Create the Presence with an existing ID
        presence.setId(1L);
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);

        int databaseSizeBeforeCreate = presenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPresenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(presenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPresences() throws Exception {
        // Initialize the database
        presenceRepository.saveAndFlush(presence);

        // Get all the presenceList
        restPresenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(presence.getId().intValue())))
            .andExpect(jsonPath("$.[*].heureDebut").value(hasItem(DEFAULT_HEURE_DEBUT)))
            .andExpect(jsonPath("$.[*].heureFin").value(hasItem(DEFAULT_HEURE_FIN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPresence() throws Exception {
        // Initialize the database
        presenceRepository.saveAndFlush(presence);

        // Get the presence
        restPresenceMockMvc
            .perform(get(ENTITY_API_URL_ID, presence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(presence.getId().intValue()))
            .andExpect(jsonPath("$.heureDebut").value(DEFAULT_HEURE_DEBUT))
            .andExpect(jsonPath("$.heureFin").value(DEFAULT_HEURE_FIN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingPresence() throws Exception {
        // Get the presence
        restPresenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPresence() throws Exception {
        // Initialize the database
        presenceRepository.saveAndFlush(presence);

        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();

        // Update the presence
        Presence updatedPresence = presenceRepository.findById(presence.getId()).get();
        // Disconnect from session so that the updates on updatedPresence are not directly saved in db
        em.detach(updatedPresence);
        updatedPresence.heureDebut(UPDATED_HEURE_DEBUT).heureFin(UPDATED_HEURE_FIN).description(UPDATED_DESCRIPTION);
        PresenceDTO presenceDTO = presenceMapper.toDto(updatedPresence);

        restPresenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, presenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(presenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
        Presence testPresence = presenceList.get(presenceList.size() - 1);
        assertThat(testPresence.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testPresence.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testPresence.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPresence() throws Exception {
        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();
        presence.setId(count.incrementAndGet());

        // Create the Presence
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPresenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, presenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(presenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPresence() throws Exception {
        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();
        presence.setId(count.incrementAndGet());

        // Create the Presence
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPresenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(presenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPresence() throws Exception {
        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();
        presence.setId(count.incrementAndGet());

        // Create the Presence
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPresenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(presenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePresenceWithPatch() throws Exception {
        // Initialize the database
        presenceRepository.saveAndFlush(presence);

        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();

        // Update the presence using partial update
        Presence partialUpdatedPresence = new Presence();
        partialUpdatedPresence.setId(presence.getId());

        partialUpdatedPresence.heureFin(UPDATED_HEURE_FIN);

        restPresenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPresence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPresence))
            )
            .andExpect(status().isOk());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
        Presence testPresence = presenceList.get(presenceList.size() - 1);
        assertThat(testPresence.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testPresence.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testPresence.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePresenceWithPatch() throws Exception {
        // Initialize the database
        presenceRepository.saveAndFlush(presence);

        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();

        // Update the presence using partial update
        Presence partialUpdatedPresence = new Presence();
        partialUpdatedPresence.setId(presence.getId());

        partialUpdatedPresence.heureDebut(UPDATED_HEURE_DEBUT).heureFin(UPDATED_HEURE_FIN).description(UPDATED_DESCRIPTION);

        restPresenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPresence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPresence))
            )
            .andExpect(status().isOk());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
        Presence testPresence = presenceList.get(presenceList.size() - 1);
        assertThat(testPresence.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testPresence.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testPresence.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPresence() throws Exception {
        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();
        presence.setId(count.incrementAndGet());

        // Create the Presence
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPresenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, presenceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(presenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPresence() throws Exception {
        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();
        presence.setId(count.incrementAndGet());

        // Create the Presence
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPresenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(presenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPresence() throws Exception {
        int databaseSizeBeforeUpdate = presenceRepository.findAll().size();
        presence.setId(count.incrementAndGet());

        // Create the Presence
        PresenceDTO presenceDTO = presenceMapper.toDto(presence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPresenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(presenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Presence in the database
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePresence() throws Exception {
        // Initialize the database
        presenceRepository.saveAndFlush(presence);

        int databaseSizeBeforeDelete = presenceRepository.findAll().size();

        // Delete the presence
        restPresenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, presence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Presence> presenceList = presenceRepository.findAll();
        assertThat(presenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
