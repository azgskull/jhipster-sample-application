package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.SeanceProgramme;
import com.mycompany.myapp.repository.SeanceProgrammeRepository;
import com.mycompany.myapp.service.SeanceProgrammeService;
import com.mycompany.myapp.service.dto.SeanceProgrammeDTO;
import com.mycompany.myapp.service.mapper.SeanceProgrammeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SeanceProgramme}.
 */
@Service
@Transactional
public class SeanceProgrammeServiceImpl implements SeanceProgrammeService {

    private final Logger log = LoggerFactory.getLogger(SeanceProgrammeServiceImpl.class);

    private final SeanceProgrammeRepository seanceProgrammeRepository;

    private final SeanceProgrammeMapper seanceProgrammeMapper;

    public SeanceProgrammeServiceImpl(SeanceProgrammeRepository seanceProgrammeRepository, SeanceProgrammeMapper seanceProgrammeMapper) {
        this.seanceProgrammeRepository = seanceProgrammeRepository;
        this.seanceProgrammeMapper = seanceProgrammeMapper;
    }

    @Override
    public SeanceProgrammeDTO save(SeanceProgrammeDTO seanceProgrammeDTO) {
        log.debug("Request to save SeanceProgramme : {}", seanceProgrammeDTO);
        SeanceProgramme seanceProgramme = seanceProgrammeMapper.toEntity(seanceProgrammeDTO);
        seanceProgramme = seanceProgrammeRepository.save(seanceProgramme);
        return seanceProgrammeMapper.toDto(seanceProgramme);
    }

    @Override
    public Optional<SeanceProgrammeDTO> partialUpdate(SeanceProgrammeDTO seanceProgrammeDTO) {
        log.debug("Request to partially update SeanceProgramme : {}", seanceProgrammeDTO);

        return seanceProgrammeRepository
            .findById(seanceProgrammeDTO.getId())
            .map(
                existingSeanceProgramme -> {
                    seanceProgrammeMapper.partialUpdate(existingSeanceProgramme, seanceProgrammeDTO);
                    return existingSeanceProgramme;
                }
            )
            .map(seanceProgrammeRepository::save)
            .map(seanceProgrammeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeanceProgrammeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SeanceProgrammes");
        return seanceProgrammeRepository.findAll(pageable).map(seanceProgrammeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeanceProgrammeDTO> findOne(Long id) {
        log.debug("Request to get SeanceProgramme : {}", id);
        return seanceProgrammeRepository.findById(id).map(seanceProgrammeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeanceProgramme : {}", id);
        seanceProgrammeRepository.deleteById(id);
    }
}
