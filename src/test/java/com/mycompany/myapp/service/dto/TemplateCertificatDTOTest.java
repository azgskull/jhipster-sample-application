package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateCertificatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateCertificatDTO.class);
        TemplateCertificatDTO templateCertificatDTO1 = new TemplateCertificatDTO();
        templateCertificatDTO1.setId(1L);
        TemplateCertificatDTO templateCertificatDTO2 = new TemplateCertificatDTO();
        assertThat(templateCertificatDTO1).isNotEqualTo(templateCertificatDTO2);
        templateCertificatDTO2.setId(templateCertificatDTO1.getId());
        assertThat(templateCertificatDTO1).isEqualTo(templateCertificatDTO2);
        templateCertificatDTO2.setId(2L);
        assertThat(templateCertificatDTO1).isNotEqualTo(templateCertificatDTO2);
        templateCertificatDTO1.setId(null);
        assertThat(templateCertificatDTO1).isNotEqualTo(templateCertificatDTO2);
    }
}
