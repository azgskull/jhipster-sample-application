package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeCertificatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCertificat.class);
        TypeCertificat typeCertificat1 = new TypeCertificat();
        typeCertificat1.setId(1L);
        TypeCertificat typeCertificat2 = new TypeCertificat();
        typeCertificat2.setId(typeCertificat1.getId());
        assertThat(typeCertificat1).isEqualTo(typeCertificat2);
        typeCertificat2.setId(2L);
        assertThat(typeCertificat1).isNotEqualTo(typeCertificat2);
        typeCertificat1.setId(null);
        assertThat(typeCertificat1).isNotEqualTo(typeCertificat2);
    }
}
