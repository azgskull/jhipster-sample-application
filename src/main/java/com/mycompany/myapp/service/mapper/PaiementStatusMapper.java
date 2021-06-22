package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PaiementStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaiementStatus} and its DTO {@link PaiementStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaiementStatusMapper extends EntityMapper<PaiementStatusDTO, PaiementStatus> {
    @Named("code")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    PaiementStatusDTO toDtoCode(PaiementStatus paiementStatus);
}
