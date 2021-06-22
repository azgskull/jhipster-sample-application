package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SportifDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sportif} and its DTO {@link SportifDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, PaysMapper.class, VilleMapper.class, TypeIdentiteMapper.class })
public interface SportifMapper extends EntityMapper<SportifDTO, Sportif> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "login")
    @Mapping(target = "pays", source = "pays", qualifiedByName = "nom")
    @Mapping(target = "ville", source = "ville", qualifiedByName = "nom")
    @Mapping(target = "typeIdentite", source = "typeIdentite", qualifiedByName = "id")
    SportifDTO toDto(Sportif s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SportifDTO toDtoId(Sportif sportif);

    @Named("nomSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    Set<SportifDTO> toDtoNomSet(Set<Sportif> sportif);
}
