package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeanceProgrammeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeanceProgramme.class);
        SeanceProgramme seanceProgramme1 = new SeanceProgramme();
        seanceProgramme1.setId(1L);
        SeanceProgramme seanceProgramme2 = new SeanceProgramme();
        seanceProgramme2.setId(seanceProgramme1.getId());
        assertThat(seanceProgramme1).isEqualTo(seanceProgramme2);
        seanceProgramme2.setId(2L);
        assertThat(seanceProgramme1).isNotEqualTo(seanceProgramme2);
        seanceProgramme1.setId(null);
        assertThat(seanceProgramme1).isNotEqualTo(seanceProgramme2);
    }
}
