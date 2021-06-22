package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CertificatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificatDTO.class);
        CertificatDTO certificatDTO1 = new CertificatDTO();
        certificatDTO1.setId(1L);
        CertificatDTO certificatDTO2 = new CertificatDTO();
        assertThat(certificatDTO1).isNotEqualTo(certificatDTO2);
        certificatDTO2.setId(certificatDTO1.getId());
        assertThat(certificatDTO1).isEqualTo(certificatDTO2);
        certificatDTO2.setId(2L);
        assertThat(certificatDTO1).isNotEqualTo(certificatDTO2);
        certificatDTO1.setId(null);
        assertThat(certificatDTO1).isNotEqualTo(certificatDTO2);
    }
}
