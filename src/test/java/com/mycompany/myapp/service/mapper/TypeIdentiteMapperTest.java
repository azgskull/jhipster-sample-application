package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeIdentiteMapperTest {

    private TypeIdentiteMapper typeIdentiteMapper;

    @BeforeEach
    public void setUp() {
        typeIdentiteMapper = new TypeIdentiteMapperImpl();
    }
}
