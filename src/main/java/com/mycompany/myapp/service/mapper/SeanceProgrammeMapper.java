package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SeanceProgrammeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SeanceProgramme} and its DTO {@link SeanceProgrammeDTO}.
 */
@Mapper(componentModel = "spring", uses = { DisciplineMapper.class, SalleMapper.class })
public interface SeanceProgrammeMapper extends EntityMapper<SeanceProgrammeDTO, SeanceProgramme> {
    @Mapping(target = "discipline", source = "discipline", qualifiedByName = "nom")
    @Mapping(target = "salle", source = "salle", qualifiedByName = "id")
    SeanceProgrammeDTO toDto(SeanceProgramme s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SeanceProgrammeDTO toDtoId(SeanceProgramme seanceProgramme);
}
