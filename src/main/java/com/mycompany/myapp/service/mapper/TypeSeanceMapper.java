package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TypeSeanceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeSeance} and its DTO {@link TypeSeanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeSeanceMapper extends EntityMapper<TypeSeanceDTO, TypeSeance> {
    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    TypeSeanceDTO toDtoNom(TypeSeance typeSeance);
}
