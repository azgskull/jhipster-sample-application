package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModePaiementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModePaiementDTO.class);
        ModePaiementDTO modePaiementDTO1 = new ModePaiementDTO();
        modePaiementDTO1.setId(1L);
        ModePaiementDTO modePaiementDTO2 = new ModePaiementDTO();
        assertThat(modePaiementDTO1).isNotEqualTo(modePaiementDTO2);
        modePaiementDTO2.setId(modePaiementDTO1.getId());
        assertThat(modePaiementDTO1).isEqualTo(modePaiementDTO2);
        modePaiementDTO2.setId(2L);
        assertThat(modePaiementDTO1).isNotEqualTo(modePaiementDTO2);
        modePaiementDTO1.setId(null);
        assertThat(modePaiementDTO1).isNotEqualTo(modePaiementDTO2);
    }
}
