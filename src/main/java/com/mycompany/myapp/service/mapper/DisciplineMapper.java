package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DisciplineDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discipline} and its DTO {@link DisciplineDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DisciplineMapper extends EntityMapper<DisciplineDTO, Discipline> {
    @Named("nomSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    Set<DisciplineDTO> toDtoNomSet(Set<Discipline> discipline);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    DisciplineDTO toDtoNom(Discipline discipline);
}
