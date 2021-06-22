package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeanceMapperTest {

    private SeanceMapper seanceMapper;

    @BeforeEach
    public void setUp() {
        seanceMapper = new SeanceMapperImpl();
    }
}
