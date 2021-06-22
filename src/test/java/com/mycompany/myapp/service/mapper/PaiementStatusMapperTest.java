package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaiementStatusMapperTest {

    private PaiementStatusMapper paiementStatusMapper;

    @BeforeEach
    public void setUp() {
        paiementStatusMapper = new PaiementStatusMapperImpl();
    }
}
