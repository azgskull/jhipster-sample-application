package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Sportif;
import com.mycompany.myapp.repository.SportifRepository;
import com.mycompany.myapp.service.SportifService;
import com.mycompany.myapp.service.dto.SportifDTO;
import com.mycompany.myapp.service.mapper.SportifMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sportif}.
 */
@Service
@Transactional
public class SportifServiceImpl implements SportifService {

    private final Logger log = LoggerFactory.getLogger(SportifServiceImpl.class);

    private final SportifRepository sportifRepository;

    private final SportifMapper sportifMapper;

    public SportifServiceImpl(SportifRepository sportifRepository, SportifMapper sportifMapper) {
        this.sportifRepository = sportifRepository;
        this.sportifMapper = sportifMapper;
    }

    @Override
    public SportifDTO save(SportifDTO sportifDTO) {
        log.debug("Request to save Sportif : {}", sportifDTO);
        Sportif sportif = sportifMapper.toEntity(sportifDTO);
        sportif = sportifRepository.save(sportif);
        return sportifMapper.toDto(sportif);
    }

    @Override
    public Optional<SportifDTO> partialUpdate(SportifDTO sportifDTO) {
        log.debug("Request to partially update Sportif : {}", sportifDTO);

        return sportifRepository
            .findById(sportifDTO.getId())
            .map(
                existingSportif -> {
                    sportifMapper.partialUpdate(existingSportif, sportifDTO);
                    return existingSportif;
                }
            )
            .map(sportifRepository::save)
            .map(sportifMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SportifDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sportifs");
        return sportifRepository.findAll(pageable).map(sportifMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SportifDTO> findOne(Long id) {
        log.debug("Request to get Sportif : {}", id);
        return sportifRepository.findById(id).map(sportifMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sportif : {}", id);
        sportifRepository.deleteById(id);
    }
}
