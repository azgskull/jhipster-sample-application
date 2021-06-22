package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TypeIdentite;
import com.mycompany.myapp.repository.TypeIdentiteRepository;
import com.mycompany.myapp.service.TypeIdentiteService;
import com.mycompany.myapp.service.dto.TypeIdentiteDTO;
import com.mycompany.myapp.service.mapper.TypeIdentiteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeIdentite}.
 */
@Service
@Transactional
public class TypeIdentiteServiceImpl implements TypeIdentiteService {

    private final Logger log = LoggerFactory.getLogger(TypeIdentiteServiceImpl.class);

    private final TypeIdentiteRepository typeIdentiteRepository;

    private final TypeIdentiteMapper typeIdentiteMapper;

    public TypeIdentiteServiceImpl(TypeIdentiteRepository typeIdentiteRepository, TypeIdentiteMapper typeIdentiteMapper) {
        this.typeIdentiteRepository = typeIdentiteRepository;
        this.typeIdentiteMapper = typeIdentiteMapper;
    }

    @Override
    public TypeIdentiteDTO save(TypeIdentiteDTO typeIdentiteDTO) {
        log.debug("Request to save TypeIdentite : {}", typeIdentiteDTO);
        TypeIdentite typeIdentite = typeIdentiteMapper.toEntity(typeIdentiteDTO);
        typeIdentite = typeIdentiteRepository.save(typeIdentite);
        return typeIdentiteMapper.toDto(typeIdentite);
    }

    @Override
    public Optional<TypeIdentiteDTO> partialUpdate(TypeIdentiteDTO typeIdentiteDTO) {
        log.debug("Request to partially update TypeIdentite : {}", typeIdentiteDTO);

        return typeIdentiteRepository
            .findById(typeIdentiteDTO.getId())
            .map(
                existingTypeIdentite -> {
                    typeIdentiteMapper.partialUpdate(existingTypeIdentite, typeIdentiteDTO);
                    return existingTypeIdentite;
                }
            )
            .map(typeIdentiteRepository::save)
            .map(typeIdentiteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeIdentiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeIdentites");
        return typeIdentiteRepository.findAll(pageable).map(typeIdentiteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeIdentiteDTO> findOne(Long id) {
        log.debug("Request to get TypeIdentite : {}", id);
        return typeIdentiteRepository.findById(id).map(typeIdentiteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeIdentite : {}", id);
        typeIdentiteRepository.deleteById(id);
    }
}
