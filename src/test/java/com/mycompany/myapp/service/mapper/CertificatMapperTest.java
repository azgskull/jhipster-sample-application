package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CertificatMapperTest {

    private CertificatMapper certificatMapper;

    @BeforeEach
    public void setUp() {
        certificatMapper = new CertificatMapperImpl();
    }
}
