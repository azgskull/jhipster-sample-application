package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcheanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Echeance.class);
        Echeance echeance1 = new Echeance();
        echeance1.setId(1L);
        Echeance echeance2 = new Echeance();
        echeance2.setId(echeance1.getId());
        assertThat(echeance1).isEqualTo(echeance2);
        echeance2.setId(2L);
        assertThat(echeance1).isNotEqualTo(echeance2);
        echeance1.setId(null);
        assertThat(echeance1).isNotEqualTo(echeance2);
    }
}
