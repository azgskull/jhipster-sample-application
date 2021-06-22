package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PaysDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pays} and its DTO {@link PaysDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaysMapper extends EntityMapper<PaysDTO, Pays> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaysDTO toDtoId(Pays pays);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    PaysDTO toDtoNom(Pays pays);
}
