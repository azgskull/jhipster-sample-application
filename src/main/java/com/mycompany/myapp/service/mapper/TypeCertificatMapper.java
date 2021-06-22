package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TypeCertificatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeCertificat} and its DTO {@link TypeCertificatDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeCertificatMapper extends EntityMapper<TypeCertificatDTO, TypeCertificat> {
    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    TypeCertificatDTO toDtoNom(TypeCertificat typeCertificat);
}
