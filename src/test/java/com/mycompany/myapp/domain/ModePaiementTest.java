package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModePaiementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModePaiement.class);
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setId(1L);
        ModePaiement modePaiement2 = new ModePaiement();
        modePaiement2.setId(modePaiement1.getId());
        assertThat(modePaiement1).isEqualTo(modePaiement2);
        modePaiement2.setId(2L);
        assertThat(modePaiement1).isNotEqualTo(modePaiement2);
        modePaiement1.setId(null);
        assertThat(modePaiement1).isNotEqualTo(modePaiement2);
    }
}
