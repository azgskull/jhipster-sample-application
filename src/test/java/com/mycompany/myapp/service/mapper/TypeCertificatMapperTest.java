package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeCertificatMapperTest {

    private TypeCertificatMapper typeCertificatMapper;

    @BeforeEach
    public void setUp() {
        typeCertificatMapper = new TypeCertificatMapperImpl();
    }
}
