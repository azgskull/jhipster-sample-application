package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateCertificatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateCertificat.class);
        TemplateCertificat templateCertificat1 = new TemplateCertificat();
        templateCertificat1.setId(1L);
        TemplateCertificat templateCertificat2 = new TemplateCertificat();
        templateCertificat2.setId(templateCertificat1.getId());
        assertThat(templateCertificat1).isEqualTo(templateCertificat2);
        templateCertificat2.setId(2L);
        assertThat(templateCertificat1).isNotEqualTo(templateCertificat2);
        templateCertificat1.setId(null);
        assertThat(templateCertificat1).isNotEqualTo(templateCertificat2);
    }
}
