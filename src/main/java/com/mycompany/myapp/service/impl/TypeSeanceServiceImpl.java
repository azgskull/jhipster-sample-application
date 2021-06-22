package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TypeSeance;
import com.mycompany.myapp.repository.TypeSeanceRepository;
import com.mycompany.myapp.service.TypeSeanceService;
import com.mycompany.myapp.service.dto.TypeSeanceDTO;
import com.mycompany.myapp.service.mapper.TypeSeanceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeSeance}.
 */
@Service
@Transactional
public class TypeSeanceServiceImpl implements TypeSeanceService {

    private final Logger log = LoggerFactory.getLogger(TypeSeanceServiceImpl.class);

    private final TypeSeanceRepository typeSeanceRepository;

    private final TypeSeanceMapper typeSeanceMapper;

    public TypeSeanceServiceImpl(TypeSeanceRepository typeSeanceRepository, TypeSeanceMapper typeSeanceMapper) {
        this.typeSeanceRepository = typeSeanceRepository;
        this.typeSeanceMapper = typeSeanceMapper;
    }

    @Override
    public TypeSeanceDTO save(TypeSeanceDTO typeSeanceDTO) {
        log.debug("Request to save TypeSeance : {}", typeSeanceDTO);
        TypeSeance typeSeance = typeSeanceMapper.toEntity(typeSeanceDTO);
        typeSeance = typeSeanceRepository.save(typeSeance);
        return typeSeanceMapper.toDto(typeSeance);
    }

    @Override
    public Optional<TypeSeanceDTO> partialUpdate(TypeSeanceDTO typeSeanceDTO) {
        log.debug("Request to partially update TypeSeance : {}", typeSeanceDTO);

        return typeSeanceRepository
            .findById(typeSeanceDTO.getId())
            .map(
                existingTypeSeance -> {
                    typeSeanceMapper.partialUpdate(existingTypeSeance, typeSeanceDTO);
                    return existingTypeSeance;
                }
            )
            .map(typeSeanceRepository::save)
            .map(typeSeanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeSeanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeSeances");
        return typeSeanceRepository.findAll(pageable).map(typeSeanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeSeanceDTO> findOne(Long id) {
        log.debug("Request to get TypeSeance : {}", id);
        return typeSeanceRepository.findById(id).map(typeSeanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeSeance : {}", id);
        typeSeanceRepository.deleteById(id);
    }
}
