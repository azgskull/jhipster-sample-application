package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SportifMapperTest {

    private SportifMapper sportifMapper;

    @BeforeEach
    public void setUp() {
        sportifMapper = new SportifMapperImpl();
    }
}
