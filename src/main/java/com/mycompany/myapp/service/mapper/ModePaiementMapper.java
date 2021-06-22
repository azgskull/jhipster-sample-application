package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ModePaiementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModePaiement} and its DTO {@link ModePaiementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModePaiementMapper extends EntityMapper<ModePaiementDTO, ModePaiement> {
    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    ModePaiementDTO toDtoNom(ModePaiement modePaiement);
}
