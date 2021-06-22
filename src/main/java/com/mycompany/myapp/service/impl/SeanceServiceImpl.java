package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Seance;
import com.mycompany.myapp.repository.SeanceRepository;
import com.mycompany.myapp.service.SeanceService;
import com.mycompany.myapp.service.dto.SeanceDTO;
import com.mycompany.myapp.service.mapper.SeanceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Seance}.
 */
@Service
@Transactional
public class SeanceServiceImpl implements SeanceService {

    private final Logger log = LoggerFactory.getLogger(SeanceServiceImpl.class);

    private final SeanceRepository seanceRepository;

    private final SeanceMapper seanceMapper;

    public SeanceServiceImpl(SeanceRepository seanceRepository, SeanceMapper seanceMapper) {
        this.seanceRepository = seanceRepository;
        this.seanceMapper = seanceMapper;
    }

    @Override
    public SeanceDTO save(SeanceDTO seanceDTO) {
        log.debug("Request to save Seance : {}", seanceDTO);
        Seance seance = seanceMapper.toEntity(seanceDTO);
        seance = seanceRepository.save(seance);
        return seanceMapper.toDto(seance);
    }

    @Override
    public Optional<SeanceDTO> partialUpdate(SeanceDTO seanceDTO) {
        log.debug("Request to partially update Seance : {}", seanceDTO);

        return seanceRepository
            .findById(seanceDTO.getId())
            .map(
                existingSeance -> {
                    seanceMapper.partialUpdate(existingSeance, seanceDTO);
                    return existingSeance;
                }
            )
            .map(seanceRepository::save)
            .map(seanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Seances");
        return seanceRepository.findAll(pageable).map(seanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeanceDTO> findOne(Long id) {
        log.debug("Request to get Seance : {}", id);
        return seanceRepository.findById(id).map(seanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seance : {}", id);
        seanceRepository.deleteById(id);
    }
}
