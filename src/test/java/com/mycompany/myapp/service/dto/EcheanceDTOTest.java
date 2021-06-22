package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcheanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcheanceDTO.class);
        EcheanceDTO echeanceDTO1 = new EcheanceDTO();
        echeanceDTO1.setId(1L);
        EcheanceDTO echeanceDTO2 = new EcheanceDTO();
        assertThat(echeanceDTO1).isNotEqualTo(echeanceDTO2);
        echeanceDTO2.setId(echeanceDTO1.getId());
        assertThat(echeanceDTO1).isEqualTo(echeanceDTO2);
        echeanceDTO2.setId(2L);
        assertThat(echeanceDTO1).isNotEqualTo(echeanceDTO2);
        echeanceDTO1.setId(null);
        assertThat(echeanceDTO1).isNotEqualTo(echeanceDTO2);
    }
}
