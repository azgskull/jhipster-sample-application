package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeanceProgrammeMapperTest {

    private SeanceProgrammeMapper seanceProgrammeMapper;

    @BeforeEach
    public void setUp() {
        seanceProgrammeMapper = new SeanceProgrammeMapperImpl();
    }
}
