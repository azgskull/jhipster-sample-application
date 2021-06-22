package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaiementStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaiementStatus.class);
        PaiementStatus paiementStatus1 = new PaiementStatus();
        paiementStatus1.setId(1L);
        PaiementStatus paiementStatus2 = new PaiementStatus();
        paiementStatus2.setId(paiementStatus1.getId());
        assertThat(paiementStatus1).isEqualTo(paiementStatus2);
        paiementStatus2.setId(2L);
        assertThat(paiementStatus1).isNotEqualTo(paiementStatus2);
        paiementStatus1.setId(null);
        assertThat(paiementStatus1).isNotEqualTo(paiementStatus2);
    }
}
