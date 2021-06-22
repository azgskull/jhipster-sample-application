package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SalleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Salle} and its DTO {@link SalleDTO}.
 */
@Mapper(componentModel = "spring", uses = { PaysMapper.class, VilleMapper.class, StructureMapper.class })
public interface SalleMapper extends EntityMapper<SalleDTO, Salle> {
    @Mapping(target = "pays", source = "pays", qualifiedByName = "nom")
    @Mapping(target = "ville", source = "ville", qualifiedByName = "nom")
    @Mapping(target = "structure", source = "structure", qualifiedByName = "id")
    SalleDTO toDto(Salle s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SalleDTO toDtoId(Salle salle);
}
