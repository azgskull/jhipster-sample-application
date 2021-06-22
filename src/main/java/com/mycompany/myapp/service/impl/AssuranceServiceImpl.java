package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Assurance;
import com.mycompany.myapp.repository.AssuranceRepository;
import com.mycompany.myapp.service.AssuranceService;
import com.mycompany.myapp.service.dto.AssuranceDTO;
import com.mycompany.myapp.service.mapper.AssuranceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Assurance}.
 */
@Service
@Transactional
public class AssuranceServiceImpl implements AssuranceService {

    private final Logger log = LoggerFactory.getLogger(AssuranceServiceImpl.class);

    private final AssuranceRepository assuranceRepository;

    private final AssuranceMapper assuranceMapper;

    public AssuranceServiceImpl(AssuranceRepository assuranceRepository, AssuranceMapper assuranceMapper) {
        this.assuranceRepository = assuranceRepository;
        this.assuranceMapper = assuranceMapper;
    }

    @Override
    public AssuranceDTO save(AssuranceDTO assuranceDTO) {
        log.debug("Request to save Assurance : {}", assuranceDTO);
        Assurance assurance = assuranceMapper.toEntity(assuranceDTO);
        assurance = assuranceRepository.save(assurance);
        return assuranceMapper.toDto(assurance);
    }

    @Override
    public Optional<AssuranceDTO> partialUpdate(AssuranceDTO assuranceDTO) {
        log.debug("Request to partially update Assurance : {}", assuranceDTO);

        return assuranceRepository
            .findById(assuranceDTO.getId())
            .map(
                existingAssurance -> {
                    assuranceMapper.partialUpdate(existingAssurance, assuranceDTO);
                    return existingAssurance;
                }
            )
            .map(assuranceRepository::save)
            .map(assuranceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssuranceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assurances");
        return assuranceRepository.findAll(pageable).map(assuranceMapper::toDto);
    }

    public Page<AssuranceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return assuranceRepository.findAllWithEagerRelationships(pageable).map(assuranceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssuranceDTO> findOne(Long id) {
        log.debug("Request to get Assurance : {}", id);
        return assuranceRepository.findOneWithEagerRelationships(id).map(assuranceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assurance : {}", id);
        assuranceRepository.deleteById(id);
    }
}
