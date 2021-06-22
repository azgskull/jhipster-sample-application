package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EcheanceDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Echeance} and its DTO {@link EcheanceDTO}.
 */
@Mapper(componentModel = "spring", uses = { AssuranceMapper.class, CertificatMapper.class, SeanceMapper.class, SportifMapper.class })
public interface EcheanceMapper extends EntityMapper<EcheanceDTO, Echeance> {
    @Mapping(target = "assurance", source = "assurance", qualifiedByName = "code")
    @Mapping(target = "certificat", source = "certificat", qualifiedByName = "nom")
    @Mapping(target = "seances", source = "seances", qualifiedByName = "nomSet")
    @Mapping(target = "sportif", source = "sportif", qualifiedByName = "id")
    EcheanceDTO toDto(Echeance s);

    @Mapping(target = "removeSeance", ignore = true)
    Echeance toEntity(EcheanceDTO echeanceDTO);

    @Named("codeSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    Set<EcheanceDTO> toDtoCodeSet(Set<Echeance> echeance);
}
