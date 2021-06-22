package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganismeAssuranceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganismeAssurance.class);
        OrganismeAssurance organismeAssurance1 = new OrganismeAssurance();
        organismeAssurance1.setId(1L);
        OrganismeAssurance organismeAssurance2 = new OrganismeAssurance();
        organismeAssurance2.setId(organismeAssurance1.getId());
        assertThat(organismeAssurance1).isEqualTo(organismeAssurance2);
        organismeAssurance2.setId(2L);
        assertThat(organismeAssurance1).isNotEqualTo(organismeAssurance2);
        organismeAssurance1.setId(null);
        assertThat(organismeAssurance1).isNotEqualTo(organismeAssurance2);
    }
}
