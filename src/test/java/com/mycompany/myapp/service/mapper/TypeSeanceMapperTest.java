package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeSeanceMapperTest {

    private TypeSeanceMapper typeSeanceMapper;

    @BeforeEach
    public void setUp() {
        typeSeanceMapper = new TypeSeanceMapperImpl();
    }
}
