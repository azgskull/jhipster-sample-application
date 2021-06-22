package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TemplateCertificatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateCertificat} and its DTO {@link TemplateCertificatDTO}.
 */
@Mapper(componentModel = "spring", uses = { DisciplineMapper.class, TypeCertificatMapper.class })
public interface TemplateCertificatMapper extends EntityMapper<TemplateCertificatDTO, TemplateCertificat> {
    @Mapping(target = "discipline", source = "discipline", qualifiedByName = "nom")
    @Mapping(target = "typeCertificat", source = "typeCertificat", qualifiedByName = "nom")
    TemplateCertificatDTO toDto(TemplateCertificat s);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    TemplateCertificatDTO toDtoNom(TemplateCertificat templateCertificat);
}
