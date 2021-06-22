package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeIdentiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeIdentite.class);
        TypeIdentite typeIdentite1 = new TypeIdentite();
        typeIdentite1.setId(1L);
        TypeIdentite typeIdentite2 = new TypeIdentite();
        typeIdentite2.setId(typeIdentite1.getId());
        assertThat(typeIdentite1).isEqualTo(typeIdentite2);
        typeIdentite2.setId(2L);
        assertThat(typeIdentite1).isNotEqualTo(typeIdentite2);
        typeIdentite1.setId(null);
        assertThat(typeIdentite1).isNotEqualTo(typeIdentite2);
    }
}
