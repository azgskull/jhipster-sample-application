package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisciplineMapperTest {

    private DisciplineMapper disciplineMapper;

    @BeforeEach
    public void setUp() {
        disciplineMapper = new DisciplineMapperImpl();
    }
}
