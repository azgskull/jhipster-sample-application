package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeanceProgrammeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeanceProgrammeDTO.class);
        SeanceProgrammeDTO seanceProgrammeDTO1 = new SeanceProgrammeDTO();
        seanceProgrammeDTO1.setId(1L);
        SeanceProgrammeDTO seanceProgrammeDTO2 = new SeanceProgrammeDTO();
        assertThat(seanceProgrammeDTO1).isNotEqualTo(seanceProgrammeDTO2);
        seanceProgrammeDTO2.setId(seanceProgrammeDTO1.getId());
        assertThat(seanceProgrammeDTO1).isEqualTo(seanceProgrammeDTO2);
        seanceProgrammeDTO2.setId(2L);
        assertThat(seanceProgrammeDTO1).isNotEqualTo(seanceProgrammeDTO2);
        seanceProgrammeDTO1.setId(null);
        assertThat(seanceProgrammeDTO1).isNotEqualTo(seanceProgrammeDTO2);
    }
}
