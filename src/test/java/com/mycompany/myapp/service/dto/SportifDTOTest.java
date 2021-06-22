package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportifDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SportifDTO.class);
        SportifDTO sportifDTO1 = new SportifDTO();
        sportifDTO1.setId(1L);
        SportifDTO sportifDTO2 = new SportifDTO();
        assertThat(sportifDTO1).isNotEqualTo(sportifDTO2);
        sportifDTO2.setId(sportifDTO1.getId());
        assertThat(sportifDTO1).isEqualTo(sportifDTO2);
        sportifDTO2.setId(2L);
        assertThat(sportifDTO1).isNotEqualTo(sportifDTO2);
        sportifDTO1.setId(null);
        assertThat(sportifDTO1).isNotEqualTo(sportifDTO2);
    }
}
