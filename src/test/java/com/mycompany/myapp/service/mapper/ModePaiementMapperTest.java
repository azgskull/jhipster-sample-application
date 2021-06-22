package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModePaiementMapperTest {

    private ModePaiementMapper modePaiementMapper;

    @BeforeEach
    public void setUp() {
        modePaiementMapper = new ModePaiementMapperImpl();
    }
}
