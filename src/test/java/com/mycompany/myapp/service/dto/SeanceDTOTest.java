package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeanceDTO.class);
        SeanceDTO seanceDTO1 = new SeanceDTO();
        seanceDTO1.setId(1L);
        SeanceDTO seanceDTO2 = new SeanceDTO();
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
        seanceDTO2.setId(seanceDTO1.getId());
        assertThat(seanceDTO1).isEqualTo(seanceDTO2);
        seanceDTO2.setId(2L);
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
        seanceDTO1.setId(null);
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
    }
}
