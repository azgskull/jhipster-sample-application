package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AssuranceDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Assurance} and its DTO {@link AssuranceDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrganismeAssuranceMapper.class, SportifMapper.class })
public interface AssuranceMapper extends EntityMapper<AssuranceDTO, Assurance> {
    @Mapping(target = "organismeAssurance", source = "organismeAssurance", qualifiedByName = "nom")
    @Mapping(target = "sportifs", source = "sportifs", qualifiedByName = "nomSet")
    AssuranceDTO toDto(Assurance s);

    @Mapping(target = "removeSportif", ignore = true)
    Assurance toEntity(AssuranceDTO assuranceDTO);

    @Named("code")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    AssuranceDTO toDtoCode(Assurance assurance);
}
