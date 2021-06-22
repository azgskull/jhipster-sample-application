package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaiementStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaiementStatusDTO.class);
        PaiementStatusDTO paiementStatusDTO1 = new PaiementStatusDTO();
        paiementStatusDTO1.setId(1L);
        PaiementStatusDTO paiementStatusDTO2 = new PaiementStatusDTO();
        assertThat(paiementStatusDTO1).isNotEqualTo(paiementStatusDTO2);
        paiementStatusDTO2.setId(paiementStatusDTO1.getId());
        assertThat(paiementStatusDTO1).isEqualTo(paiementStatusDTO2);
        paiementStatusDTO2.setId(2L);
        assertThat(paiementStatusDTO1).isNotEqualTo(paiementStatusDTO2);
        paiementStatusDTO1.setId(null);
        assertThat(paiementStatusDTO1).isNotEqualTo(paiementStatusDTO2);
    }
}
