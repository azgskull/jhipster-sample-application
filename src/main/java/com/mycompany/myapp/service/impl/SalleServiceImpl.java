package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Salle;
import com.mycompany.myapp.repository.SalleRepository;
import com.mycompany.myapp.service.SalleService;
import com.mycompany.myapp.service.dto.SalleDTO;
import com.mycompany.myapp.service.mapper.SalleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Salle}.
 */
@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    private final Logger log = LoggerFactory.getLogger(SalleServiceImpl.class);

    private final SalleRepository salleRepository;

    private final SalleMapper salleMapper;

    public SalleServiceImpl(SalleRepository salleRepository, SalleMapper salleMapper) {
        this.salleRepository = salleRepository;
        this.salleMapper = salleMapper;
    }

    @Override
    public SalleDTO save(SalleDTO salleDTO) {
        log.debug("Request to save Salle : {}", salleDTO);
        Salle salle = salleMapper.toEntity(salleDTO);
        salle = salleRepository.save(salle);
        return salleMapper.toDto(salle);
    }

    @Override
    public Optional<SalleDTO> partialUpdate(SalleDTO salleDTO) {
        log.debug("Request to partially update Salle : {}", salleDTO);

        return salleRepository
            .findById(salleDTO.getId())
            .map(
                existingSalle -> {
                    salleMapper.partialUpdate(existingSalle, salleDTO);
                    return existingSalle;
                }
            )
            .map(salleRepository::save)
            .map(salleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Salles");
        return salleRepository.findAll(pageable).map(salleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalleDTO> findOne(Long id) {
        log.debug("Request to get Salle : {}", id);
        return salleRepository.findById(id).map(salleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Salle : {}", id);
        salleRepository.deleteById(id);
    }
}
