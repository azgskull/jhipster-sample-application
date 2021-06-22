package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PaiementStatus;
import com.mycompany.myapp.repository.PaiementStatusRepository;
import com.mycompany.myapp.service.PaiementStatusService;
import com.mycompany.myapp.service.dto.PaiementStatusDTO;
import com.mycompany.myapp.service.mapper.PaiementStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaiementStatus}.
 */
@Service
@Transactional
public class PaiementStatusServiceImpl implements PaiementStatusService {

    private final Logger log = LoggerFactory.getLogger(PaiementStatusServiceImpl.class);

    private final PaiementStatusRepository paiementStatusRepository;

    private final PaiementStatusMapper paiementStatusMapper;

    public PaiementStatusServiceImpl(PaiementStatusRepository paiementStatusRepository, PaiementStatusMapper paiementStatusMapper) {
        this.paiementStatusRepository = paiementStatusRepository;
        this.paiementStatusMapper = paiementStatusMapper;
    }

    @Override
    public PaiementStatusDTO save(PaiementStatusDTO paiementStatusDTO) {
        log.debug("Request to save PaiementStatus : {}", paiementStatusDTO);
        PaiementStatus paiementStatus = paiementStatusMapper.toEntity(paiementStatusDTO);
        paiementStatus = paiementStatusRepository.save(paiementStatus);
        return paiementStatusMapper.toDto(paiementStatus);
    }

    @Override
    public Optional<PaiementStatusDTO> partialUpdate(PaiementStatusDTO paiementStatusDTO) {
        log.debug("Request to partially update PaiementStatus : {}", paiementStatusDTO);

        return paiementStatusRepository
            .findById(paiementStatusDTO.getId())
            .map(
                existingPaiementStatus -> {
                    paiementStatusMapper.partialUpdate(existingPaiementStatus, paiementStatusDTO);
                    return existingPaiementStatus;
                }
            )
            .map(paiementStatusRepository::save)
            .map(paiementStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaiementStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaiementStatuses");
        return paiementStatusRepository.findAll(pageable).map(paiementStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaiementStatusDTO> findOne(Long id) {
        log.debug("Request to get PaiementStatus : {}", id);
        return paiementStatusRepository.findById(id).map(paiementStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaiementStatus : {}", id);
        paiementStatusRepository.deleteById(id);
    }
}
