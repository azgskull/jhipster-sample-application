package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssuranceMapperTest {

    private AssuranceMapper assuranceMapper;

    @BeforeEach
    public void setUp() {
        assuranceMapper = new AssuranceMapperImpl();
    }
}
