package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PresenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Presence} and its DTO {@link PresenceDTO}.
 */
@Mapper(componentModel = "spring", uses = { RoleMapper.class, SeanceMapper.class, SportifMapper.class })
public interface PresenceMapper extends EntityMapper<PresenceDTO, Presence> {
    @Mapping(target = "role", source = "role", qualifiedByName = "nom")
    @Mapping(target = "seance", source = "seance", qualifiedByName = "id")
    @Mapping(target = "sportif", source = "sportif", qualifiedByName = "id")
    PresenceDTO toDto(Presence s);
}
