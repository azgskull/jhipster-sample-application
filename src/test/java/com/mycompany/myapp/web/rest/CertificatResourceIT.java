package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Certificat;
import com.mycompany.myapp.repository.CertificatRepository;
import com.mycompany.myapp.service.dto.CertificatDTO;
import com.mycompany.myapp.service.mapper.CertificatMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CertificatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CertificatResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FICHIER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/certificats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CertificatRepository certificatRepository;

    @Autowired
    private CertificatMapper certificatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificatMockMvc;

    private Certificat certificat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certificat createEntity(EntityManager em) {
        Certificat certificat = new Certificat()
            .code(DEFAULT_CODE)
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .dateFin(DEFAULT_DATE_FIN)
            .fichier(DEFAULT_FICHIER)
            .fichierContentType(DEFAULT_FICHIER_CONTENT_TYPE);
        return certificat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certificat createUpdatedEntity(EntityManager em) {
        Certificat certificat = new Certificat()
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .dateFin(UPDATED_DATE_FIN)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);
        return certificat;
    }

    @BeforeEach
    public void initTest() {
        certificat = createEntity(em);
    }

    @Test
    @Transactional
    void createCertificat() throws Exception {
        int databaseSizeBeforeCreate = certificatRepository.findAll().size();
        // Create the Certificat
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);
        restCertificatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificatDTO)))
            .andExpect(status().isCreated());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeCreate + 1);
        Certificat testCertificat = certificatList.get(certificatList.size() - 1);
        assertThat(testCertificat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCertificat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCertificat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCertificat.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCertificat.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testCertificat.getFichier()).isEqualTo(DEFAULT_FICHIER);
        assertThat(testCertificat.getFichierContentType()).isEqualTo(DEFAULT_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createCertificatWithExistingId() throws Exception {
        // Create the Certificat with an existing ID
        certificat.setId(1L);
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        int databaseSizeBeforeCreate = certificatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificatRepository.findAll().size();
        // set the field null
        certificat.setCode(null);

        // Create the Certificat, which fails.
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        restCertificatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificatDTO)))
            .andExpect(status().isBadRequest());

        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificatRepository.findAll().size();
        // set the field null
        certificat.setNom(null);

        // Create the Certificat, which fails.
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        restCertificatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificatDTO)))
            .andExpect(status().isBadRequest());

        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificatRepository.findAll().size();
        // set the field null
        certificat.setDate(null);

        // Create the Certificat, which fails.
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        restCertificatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificatDTO)))
            .andExpect(status().isBadRequest());

        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCertificats() throws Exception {
        // Initialize the database
        certificatRepository.saveAndFlush(certificat);

        // Get all the certificatList
        restCertificatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].fichierContentType").value(hasItem(DEFAULT_FICHIER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER))));
    }

    @Test
    @Transactional
    void getCertificat() throws Exception {
        // Initialize the database
        certificatRepository.saveAndFlush(certificat);

        // Get the certificat
        restCertificatMockMvc
            .perform(get(ENTITY_API_URL_ID, certificat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certificat.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.fichierContentType").value(DEFAULT_FICHIER_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier").value(Base64Utils.encodeToString(DEFAULT_FICHIER)));
    }

    @Test
    @Transactional
    void getNonExistingCertificat() throws Exception {
        // Get the certificat
        restCertificatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCertificat() throws Exception {
        // Initialize the database
        certificatRepository.saveAndFlush(certificat);

        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();

        // Update the certificat
        Certificat updatedCertificat = certificatRepository.findById(certificat.getId()).get();
        // Disconnect from session so that the updates on updatedCertificat are not directly saved in db
        em.detach(updatedCertificat);
        updatedCertificat
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .dateFin(UPDATED_DATE_FIN)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);
        CertificatDTO certificatDTO = certificatMapper.toDto(updatedCertificat);

        restCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificatDTO))
            )
            .andExpect(status().isOk());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
        Certificat testCertificat = certificatList.get(certificatList.size() - 1);
        assertThat(testCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCertificat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCertificat.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCertificat.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCertificat.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testCertificat.getFichierContentType()).isEqualTo(UPDATED_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCertificat() throws Exception {
        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();
        certificat.setId(count.incrementAndGet());

        // Create the Certificat
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCertificat() throws Exception {
        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();
        certificat.setId(count.incrementAndGet());

        // Create the Certificat
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCertificat() throws Exception {
        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();
        certificat.setId(count.incrementAndGet());

        // Create the Certificat
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCertificatWithPatch() throws Exception {
        // Initialize the database
        certificatRepository.saveAndFlush(certificat);

        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();

        // Update the certificat using partial update
        Certificat partialUpdatedCertificat = new Certificat();
        partialUpdatedCertificat.setId(certificat.getId());

        partialUpdatedCertificat
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .dateFin(UPDATED_DATE_FIN)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);

        restCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertificat))
            )
            .andExpect(status().isOk());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
        Certificat testCertificat = certificatList.get(certificatList.size() - 1);
        assertThat(testCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCertificat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCertificat.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCertificat.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCertificat.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testCertificat.getFichierContentType()).isEqualTo(UPDATED_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCertificatWithPatch() throws Exception {
        // Initialize the database
        certificatRepository.saveAndFlush(certificat);

        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();

        // Update the certificat using partial update
        Certificat partialUpdatedCertificat = new Certificat();
        partialUpdatedCertificat.setId(certificat.getId());

        partialUpdatedCertificat
            .code(UPDATED_CODE)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .dateFin(UPDATED_DATE_FIN)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);

        restCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertificat))
            )
            .andExpect(status().isOk());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
        Certificat testCertificat = certificatList.get(certificatList.size() - 1);
        assertThat(testCertificat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCertificat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCertificat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCertificat.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCertificat.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCertificat.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testCertificat.getFichierContentType()).isEqualTo(UPDATED_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCertificat() throws Exception {
        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();
        certificat.setId(count.incrementAndGet());

        // Create the Certificat
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, certificatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCertificat() throws Exception {
        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();
        certificat.setId(count.incrementAndGet());

        // Create the Certificat
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCertificat() throws Exception {
        int databaseSizeBeforeUpdate = certificatRepository.findAll().size();
        certificat.setId(count.incrementAndGet());

        // Create the Certificat
        CertificatDTO certificatDTO = certificatMapper.toDto(certificat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(certificatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Certificat in the database
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCertificat() throws Exception {
        // Initialize the database
        certificatRepository.saveAndFlush(certificat);

        int databaseSizeBeforeDelete = certificatRepository.findAll().size();

        // Delete the certificat
        restCertificatMockMvc
            .perform(delete(ENTITY_API_URL_ID, certificat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certificat> certificatList = certificatRepository.findAll();
        assertThat(certificatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
