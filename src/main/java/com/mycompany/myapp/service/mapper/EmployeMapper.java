package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EmployeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employe} and its DTO {@link EmployeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, TypeIdentiteMapper.class, SalleMapper.class })
public interface EmployeMapper extends EntityMapper<EmployeDTO, Employe> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "login")
    @Mapping(target = "typeIdentite", source = "typeIdentite", qualifiedByName = "id")
    @Mapping(target = "salle", source = "salle", qualifiedByName = "id")
    EmployeDTO toDto(Employe s);
}
