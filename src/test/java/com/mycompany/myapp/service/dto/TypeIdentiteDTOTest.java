package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeIdentiteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeIdentiteDTO.class);
        TypeIdentiteDTO typeIdentiteDTO1 = new TypeIdentiteDTO();
        typeIdentiteDTO1.setId(1L);
        TypeIdentiteDTO typeIdentiteDTO2 = new TypeIdentiteDTO();
        assertThat(typeIdentiteDTO1).isNotEqualTo(typeIdentiteDTO2);
        typeIdentiteDTO2.setId(typeIdentiteDTO1.getId());
        assertThat(typeIdentiteDTO1).isEqualTo(typeIdentiteDTO2);
        typeIdentiteDTO2.setId(2L);
        assertThat(typeIdentiteDTO1).isNotEqualTo(typeIdentiteDTO2);
        typeIdentiteDTO1.setId(null);
        assertThat(typeIdentiteDTO1).isNotEqualTo(typeIdentiteDTO2);
    }
}
