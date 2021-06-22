package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeCertificatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCertificatDTO.class);
        TypeCertificatDTO typeCertificatDTO1 = new TypeCertificatDTO();
        typeCertificatDTO1.setId(1L);
        TypeCertificatDTO typeCertificatDTO2 = new TypeCertificatDTO();
        assertThat(typeCertificatDTO1).isNotEqualTo(typeCertificatDTO2);
        typeCertificatDTO2.setId(typeCertificatDTO1.getId());
        assertThat(typeCertificatDTO1).isEqualTo(typeCertificatDTO2);
        typeCertificatDTO2.setId(2L);
        assertThat(typeCertificatDTO1).isNotEqualTo(typeCertificatDTO2);
        typeCertificatDTO1.setId(null);
        assertThat(typeCertificatDTO1).isNotEqualTo(typeCertificatDTO2);
    }
}
