package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AdhesionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adhesion} and its DTO {@link AdhesionDTO}.
 */
@Mapper(componentModel = "spring", uses = { RoleMapper.class, SeanceProgrammeMapper.class, SportifMapper.class })
public interface AdhesionMapper extends EntityMapper<AdhesionDTO, Adhesion> {
    @Mapping(target = "role", source = "role", qualifiedByName = "nom")
    @Mapping(target = "seanceProgramme", source = "seanceProgramme", qualifiedByName = "id")
    @Mapping(target = "sportif", source = "sportif", qualifiedByName = "id")
    AdhesionDTO toDto(Adhesion s);
}
