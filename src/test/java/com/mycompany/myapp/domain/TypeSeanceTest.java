package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeSeanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeSeance.class);
        TypeSeance typeSeance1 = new TypeSeance();
        typeSeance1.setId(1L);
        TypeSeance typeSeance2 = new TypeSeance();
        typeSeance2.setId(typeSeance1.getId());
        assertThat(typeSeance1).isEqualTo(typeSeance2);
        typeSeance2.setId(2L);
        assertThat(typeSeance1).isNotEqualTo(typeSeance2);
        typeSeance1.setId(null);
        assertThat(typeSeance1).isNotEqualTo(typeSeance2);
    }
}
