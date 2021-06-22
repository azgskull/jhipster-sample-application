package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TypeIdentiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeIdentite} and its DTO {@link TypeIdentiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeIdentiteMapper extends EntityMapper<TypeIdentiteDTO, TypeIdentite> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypeIdentiteDTO toDtoId(TypeIdentite typeIdentite);
}
