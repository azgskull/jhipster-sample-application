package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    RoleDTO toDtoNom(Role role);
}
