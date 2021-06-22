package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Structure;
import com.mycompany.myapp.repository.StructureRepository;
import com.mycompany.myapp.service.StructureService;
import com.mycompany.myapp.service.dto.StructureDTO;
import com.mycompany.myapp.service.mapper.StructureMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link StructureResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StructureResourceIT {

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

    private static final String ENTITY_API_URL = "/api/structures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StructureRepository structureRepository;

    @Mock
    private StructureRepository structureRepositoryMock;

    @Autowired
    private StructureMapper structureMapper;

    @Mock
    private StructureService structureServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStructureMockMvc;

    private Structure structure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Structure createEntity(EntityManager em) {
        Structure structure = new Structure()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .siteWeb(DEFAULT_SITE_WEB)
            .adresse(DEFAULT_ADRESSE)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE);
        return structure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Structure createUpdatedEntity(EntityManager em) {
        Structure structure = new Structure()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .siteWeb(UPDATED_SITE_WEB)
            .adresse(UPDATED_ADRESSE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE);
        return structure;
    }

    @BeforeEach
    public void initTest() {
        structure = createEntity(em);
    }

    @Test
    @Transactional
    void createStructure() throws Exception {
        int databaseSizeBeforeCreate = structureRepository.findAll().size();
        // Create the Structure
        StructureDTO structureDTO = structureMapper.toDto(structure);
        restStructureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(structureDTO)))
            .andExpect(status().isCreated());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate + 1);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testStructure.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStructure.getSiteWeb()).isEqualTo(DEFAULT_SITE_WEB);
        assertThat(testStructure.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testStructure.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testStructure.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testStructure.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStructure.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    void createStructureWithExistingId() throws Exception {
        // Create the Structure with an existing ID
        structure.setId(1L);
        StructureDTO structureDTO = structureMapper.toDto(structure);

        int databaseSizeBeforeCreate = structureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStructureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(structureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setNom(null);

        // Create the Structure, which fails.
        StructureDTO structureDTO = structureMapper.toDto(structure);

        restStructureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(structureDTO)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStructures() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList
        restStructureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(structure.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].siteWeb").value(hasItem(DEFAULT_SITE_WEB)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStructuresWithEagerRelationshipsIsEnabled() throws Exception {
        when(structureServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStructureMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(structureServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStructuresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(structureServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStructureMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(structureServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get the structure
        restStructureMockMvc
            .perform(get(ENTITY_API_URL_ID, structure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(structure.getId().intValue()))
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
    void getNonExistingStructure() throws Exception {
        // Get the structure
        restStructureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure
        Structure updatedStructure = structureRepository.findById(structure.getId()).get();
        // Disconnect from session so that the updates on updatedStructure are not directly saved in db
        em.detach(updatedStructure);
        updatedStructure
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .siteWeb(UPDATED_SITE_WEB)
            .adresse(UPDATED_ADRESSE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE);
        StructureDTO structureDTO = structureMapper.toDto(updatedStructure);

        restStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, structureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(structureDTO))
            )
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testStructure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStructure.getSiteWeb()).isEqualTo(UPDATED_SITE_WEB);
        assertThat(testStructure.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testStructure.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testStructure.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testStructure.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStructure.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void putNonExistingStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // Create the Structure
        StructureDTO structureDTO = structureMapper.toDto(structure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, structureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(structureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // Create the Structure
        StructureDTO structureDTO = structureMapper.toDto(structure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(structureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // Create the Structure
        StructureDTO structureDTO = structureMapper.toDto(structure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(structureDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStructureWithPatch() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure using partial update
        Structure partialUpdatedStructure = new Structure();
        partialUpdatedStructure.setId(structure.getId());

        partialUpdatedStructure.siteWeb(UPDATED_SITE_WEB).adresse(UPDATED_ADRESSE).email(UPDATED_EMAIL).telephone(UPDATED_TELEPHONE);

        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStructure))
            )
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testStructure.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStructure.getSiteWeb()).isEqualTo(UPDATED_SITE_WEB);
        assertThat(testStructure.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testStructure.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testStructure.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testStructure.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStructure.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void fullUpdateStructureWithPatch() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure using partial update
        Structure partialUpdatedStructure = new Structure();
        partialUpdatedStructure.setId(structure.getId());

        partialUpdatedStructure
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .siteWeb(UPDATED_SITE_WEB)
            .adresse(UPDATED_ADRESSE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE);

        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStructure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStructure))
            )
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testStructure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStructure.getSiteWeb()).isEqualTo(UPDATED_SITE_WEB);
        assertThat(testStructure.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testStructure.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testStructure.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testStructure.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStructure.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void patchNonExistingStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // Create the Structure
        StructureDTO structureDTO = structureMapper.toDto(structure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, structureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(structureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // Create the Structure
        StructureDTO structureDTO = structureMapper.toDto(structure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(structureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();
        structure.setId(count.incrementAndGet());

        // Create the Structure
        StructureDTO structureDTO = structureMapper.toDto(structure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStructureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(structureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        int databaseSizeBeforeDelete = structureRepository.findAll().size();

        // Delete the structure
        restStructureMockMvc
            .perform(delete(ENTITY_API_URL_ID, structure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
