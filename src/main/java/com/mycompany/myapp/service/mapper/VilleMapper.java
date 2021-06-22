package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.VilleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ville} and its DTO {@link VilleDTO}.
 */
@Mapper(componentModel = "spring", uses = { PaysMapper.class })
public interface VilleMapper extends EntityMapper<VilleDTO, Ville> {
    @Mapping(target = "pays", source = "pays", qualifiedByName = "id")
    VilleDTO toDto(Ville s);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    VilleDTO toDtoNom(Ville ville);
}
