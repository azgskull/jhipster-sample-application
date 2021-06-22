package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Discipline;
import com.mycompany.myapp.repository.DisciplineRepository;
import com.mycompany.myapp.service.dto.DisciplineDTO;
import com.mycompany.myapp.service.mapper.DisciplineMapper;
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
 * Integration tests for the {@link DisciplineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DisciplineResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/disciplines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineMapper disciplineMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisciplineMockMvc;

    private Discipline discipline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discipline createEntity(EntityManager em) {
        Discipline discipline = new Discipline().code(DEFAULT_CODE).nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION).photo(DEFAULT_PHOTO);
        return discipline;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discipline createUpdatedEntity(EntityManager em) {
        Discipline discipline = new Discipline().code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).photo(UPDATED_PHOTO);
        return discipline;
    }

    @BeforeEach
    public void initTest() {
        discipline = createEntity(em);
    }

    @Test
    @Transactional
    void createDiscipline() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();
        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);
        restDisciplineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isCreated());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate + 1);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDiscipline.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDiscipline.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiscipline.getPhoto()).isEqualTo(DEFAULT_PHOTO);
    }

    @Test
    @Transactional
    void createDisciplineWithExistingId() throws Exception {
        // Create the Discipline with an existing ID
        discipline.setId(1L);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplineRepository.findAll().size();
        // set the field null
        discipline.setCode(null);

        // Create the Discipline, which fails.
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        restDisciplineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplineRepository.findAll().size();
        // set the field null
        discipline.setNom(null);

        // Create the Discipline, which fails.
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        restDisciplineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDisciplines() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList
        restDisciplineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get the discipline
        restDisciplineMockMvc
            .perform(get(ENTITY_API_URL_ID, discipline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discipline.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO));
    }

    @Test
    @Transactional
    void getNonExistingDiscipline() throws Exception {
        // Get the discipline
        restDisciplineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Update the discipline
        Discipline updatedDiscipline = disciplineRepository.findById(discipline.getId()).get();
        // Disconnect from session so that the updates on updatedDiscipline are not directly saved in db
        em.detach(updatedDiscipline);
        updatedDiscipline.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).photo(UPDATED_PHOTO);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(updatedDiscipline);

        restDisciplineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disciplineDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplineDTO))
            )
            .andExpect(status().isOk());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiscipline.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDiscipline.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiscipline.getPhoto()).isEqualTo(UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void putNonExistingDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();
        discipline.setId(count.incrementAndGet());

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disciplineDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();
        discipline.setId(count.incrementAndGet());

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(disciplineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();
        discipline.setId(count.incrementAndGet());

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDisciplineWithPatch() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Update the discipline using partial update
        Discipline partialUpdatedDiscipline = new Discipline();
        partialUpdatedDiscipline.setId(discipline.getId());

        partialUpdatedDiscipline.code(UPDATED_CODE).nom(UPDATED_NOM);

        restDisciplineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscipline.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiscipline))
            )
            .andExpect(status().isOk());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiscipline.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDiscipline.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiscipline.getPhoto()).isEqualTo(DEFAULT_PHOTO);
    }

    @Test
    @Transactional
    void fullUpdateDisciplineWithPatch() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Update the discipline using partial update
        Discipline partialUpdatedDiscipline = new Discipline();
        partialUpdatedDiscipline.setId(discipline.getId());

        partialUpdatedDiscipline.code(UPDATED_CODE).nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).photo(UPDATED_PHOTO);

        restDisciplineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiscipline.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiscipline))
            )
            .andExpect(status().isOk());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiscipline.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDiscipline.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiscipline.getPhoto()).isEqualTo(UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void patchNonExistingDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();
        discipline.setId(count.incrementAndGet());

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disciplineDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disciplineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();
        discipline.setId(count.incrementAndGet());

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(disciplineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();
        discipline.setId(count.incrementAndGet());

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisciplineMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(disciplineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeDelete = disciplineRepository.findAll().size();

        // Delete the discipline
        restDisciplineMockMvc
            .perform(delete(ENTITY_API_URL_ID, discipline.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
