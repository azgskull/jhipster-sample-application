package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganismeAssuranceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganismeAssuranceDTO.class);
        OrganismeAssuranceDTO organismeAssuranceDTO1 = new OrganismeAssuranceDTO();
        organismeAssuranceDTO1.setId(1L);
        OrganismeAssuranceDTO organismeAssuranceDTO2 = new OrganismeAssuranceDTO();
        assertThat(organismeAssuranceDTO1).isNotEqualTo(organismeAssuranceDTO2);
        organismeAssuranceDTO2.setId(organismeAssuranceDTO1.getId());
        assertThat(organismeAssuranceDTO1).isEqualTo(organismeAssuranceDTO2);
        organismeAssuranceDTO2.setId(2L);
        assertThat(organismeAssuranceDTO1).isNotEqualTo(organismeAssuranceDTO2);
        organismeAssuranceDTO1.setId(null);
        assertThat(organismeAssuranceDTO1).isNotEqualTo(organismeAssuranceDTO2);
    }
}
