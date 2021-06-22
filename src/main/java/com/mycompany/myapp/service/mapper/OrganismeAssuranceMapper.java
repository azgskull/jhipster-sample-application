package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrganismeAssuranceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganismeAssurance} and its DTO {@link OrganismeAssuranceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganismeAssuranceMapper extends EntityMapper<OrganismeAssuranceDTO, OrganismeAssurance> {
    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    OrganismeAssuranceDTO toDtoNom(OrganismeAssurance organismeAssurance);
}
