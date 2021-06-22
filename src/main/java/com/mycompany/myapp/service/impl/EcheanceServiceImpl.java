package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Echeance;
import com.mycompany.myapp.repository.EcheanceRepository;
import com.mycompany.myapp.service.EcheanceService;
import com.mycompany.myapp.service.dto.EcheanceDTO;
import com.mycompany.myapp.service.mapper.EcheanceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Echeance}.
 */
@Service
@Transactional
public class EcheanceServiceImpl implements EcheanceService {

    private final Logger log = LoggerFactory.getLogger(EcheanceServiceImpl.class);

    private final EcheanceRepository echeanceRepository;

    private final EcheanceMapper echeanceMapper;

    public EcheanceServiceImpl(EcheanceRepository echeanceRepository, EcheanceMapper echeanceMapper) {
        this.echeanceRepository = echeanceRepository;
        this.echeanceMapper = echeanceMapper;
    }

    @Override
    public EcheanceDTO save(EcheanceDTO echeanceDTO) {
        log.debug("Request to save Echeance : {}", echeanceDTO);
        Echeance echeance = echeanceMapper.toEntity(echeanceDTO);
        echeance = echeanceRepository.save(echeance);
        return echeanceMapper.toDto(echeance);
    }

    @Override
    public Optional<EcheanceDTO> partialUpdate(EcheanceDTO echeanceDTO) {
        log.debug("Request to partially update Echeance : {}", echeanceDTO);

        return echeanceRepository
            .findById(echeanceDTO.getId())
            .map(
                existingEcheance -> {
                    echeanceMapper.partialUpdate(existingEcheance, echeanceDTO);
                    return existingEcheance;
                }
            )
            .map(echeanceRepository::save)
            .map(echeanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EcheanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Echeances");
        return echeanceRepository.findAll(pageable).map(echeanceMapper::toDto);
    }

    public Page<EcheanceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return echeanceRepository.findAllWithEagerRelationships(pageable).map(echeanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EcheanceDTO> findOne(Long id) {
        log.debug("Request to get Echeance : {}", id);
        return echeanceRepository.findOneWithEagerRelationships(id).map(echeanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Echeance : {}", id);
        echeanceRepository.deleteById(id);
    }
}
