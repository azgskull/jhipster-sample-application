package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SeanceDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Seance} and its DTO {@link SeanceDTO}.
 */
@Mapper(componentModel = "spring", uses = { TypeSeanceMapper.class, SeanceProgrammeMapper.class })
public interface SeanceMapper extends EntityMapper<SeanceDTO, Seance> {
    @Mapping(target = "typeSeance", source = "typeSeance", qualifiedByName = "nom")
    @Mapping(target = "seanceProgramme", source = "seanceProgramme", qualifiedByName = "id")
    SeanceDTO toDto(Seance s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SeanceDTO toDtoId(Seance seance);

    @Named("nomSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    Set<SeanceDTO> toDtoNomSet(Set<Seance> seance);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    SeanceDTO toDtoNom(Seance seance);
}
