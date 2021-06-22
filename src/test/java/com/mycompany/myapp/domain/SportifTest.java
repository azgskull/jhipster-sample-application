package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportifTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sportif.class);
        Sportif sportif1 = new Sportif();
        sportif1.setId(1L);
        Sportif sportif2 = new Sportif();
        sportif2.setId(sportif1.getId());
        assertThat(sportif1).isEqualTo(sportif2);
        sportif2.setId(2L);
        assertThat(sportif1).isNotEqualTo(sportif2);
        sportif1.setId(null);
        assertThat(sportif1).isNotEqualTo(sportif2);
    }
}
