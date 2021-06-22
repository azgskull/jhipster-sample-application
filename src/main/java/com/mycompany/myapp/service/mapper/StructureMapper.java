package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.StructureDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Structure} and its DTO {@link StructureDTO}.
 */
@Mapper(componentModel = "spring", uses = { PaysMapper.class, VilleMapper.class, DisciplineMapper.class })
public interface StructureMapper extends EntityMapper<StructureDTO, Structure> {
    @Mapping(target = "pays", source = "pays", qualifiedByName = "nom")
    @Mapping(target = "ville", source = "ville", qualifiedByName = "nom")
    @Mapping(target = "disciplines", source = "disciplines", qualifiedByName = "nomSet")
    StructureDTO toDto(Structure s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StructureDTO toDtoId(Structure structure);

    @Mapping(target = "removeDiscipline", ignore = true)
    Structure toEntity(StructureDTO structureDTO);
}
