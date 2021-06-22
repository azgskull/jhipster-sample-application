package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PaiementDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring", uses = { ModePaiementMapper.class, PaiementStatusMapper.class, EcheanceMapper.class })
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {
    @Mapping(target = "modePaiement", source = "modePaiement", qualifiedByName = "nom")
    @Mapping(target = "status", source = "status", qualifiedByName = "code")
    @Mapping(target = "echeances", source = "echeances", qualifiedByName = "codeSet")
    PaiementDTO toDto(Paiement s);

    @Mapping(target = "removeEcheance", ignore = true)
    Paiement toEntity(PaiementDTO paiementDTO);
}
