package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeSeanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeSeanceDTO.class);
        TypeSeanceDTO typeSeanceDTO1 = new TypeSeanceDTO();
        typeSeanceDTO1.setId(1L);
        TypeSeanceDTO typeSeanceDTO2 = new TypeSeanceDTO();
        assertThat(typeSeanceDTO1).isNotEqualTo(typeSeanceDTO2);
        typeSeanceDTO2.setId(typeSeanceDTO1.getId());
        assertThat(typeSeanceDTO1).isEqualTo(typeSeanceDTO2);
        typeSeanceDTO2.setId(2L);
        assertThat(typeSeanceDTO1).isNotEqualTo(typeSeanceDTO2);
        typeSeanceDTO1.setId(null);
        assertThat(typeSeanceDTO1).isNotEqualTo(typeSeanceDTO2);
    }
}
