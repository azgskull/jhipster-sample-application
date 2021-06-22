package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CertificatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certificat} and its DTO {@link CertificatDTO}.
 */
@Mapper(componentModel = "spring", uses = { TemplateCertificatMapper.class, SeanceMapper.class, SportifMapper.class })
public interface CertificatMapper extends EntityMapper<CertificatDTO, Certificat> {
    @Mapping(target = "templateCertificat", source = "templateCertificat", qualifiedByName = "nom")
    @Mapping(target = "seance", source = "seance", qualifiedByName = "nom")
    @Mapping(target = "sportif", source = "sportif", qualifiedByName = "id")
    CertificatDTO toDto(Certificat s);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    CertificatDTO toDtoNom(Certificat certificat);
}
