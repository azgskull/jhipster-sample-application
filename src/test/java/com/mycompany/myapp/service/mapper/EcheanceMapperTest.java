package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EcheanceMapperTest {

    private EcheanceMapper echeanceMapper;

    @BeforeEach
    public void setUp() {
        echeanceMapper = new EcheanceMapperImpl();
    }
}
