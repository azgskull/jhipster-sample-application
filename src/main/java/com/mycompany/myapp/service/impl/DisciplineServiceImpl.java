package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Discipline;
import com.mycompany.myapp.repository.DisciplineRepository;
import com.mycompany.myapp.service.DisciplineService;
import com.mycompany.myapp.service.dto.DisciplineDTO;
import com.mycompany.myapp.service.mapper.DisciplineMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Discipline}.
 */
@Service
@Transactional
public class DisciplineServiceImpl implements DisciplineService {

    private final Logger log = LoggerFactory.getLogger(DisciplineServiceImpl.class);

    private final DisciplineRepository disciplineRepository;

    private final DisciplineMapper disciplineMapper;

    public DisciplineServiceImpl(DisciplineRepository disciplineRepository, DisciplineMapper disciplineMapper) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
    }

    @Override
    public DisciplineDTO save(DisciplineDTO disciplineDTO) {
        log.debug("Request to save Discipline : {}", disciplineDTO);
        Discipline discipline = disciplineMapper.toEntity(disciplineDTO);
        discipline = disciplineRepository.save(discipline);
        return disciplineMapper.toDto(discipline);
    }

    @Override
    public Optional<DisciplineDTO> partialUpdate(DisciplineDTO disciplineDTO) {
        log.debug("Request to partially update Discipline : {}", disciplineDTO);

        return disciplineRepository
            .findById(disciplineDTO.getId())
            .map(
                existingDiscipline -> {
                    disciplineMapper.partialUpdate(existingDiscipline, disciplineDTO);
                    return existingDiscipline;
                }
            )
            .map(disciplineRepository::save)
            .map(disciplineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disciplines");
        return disciplineRepository.findAll(pageable).map(disciplineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisciplineDTO> findOne(Long id) {
        log.debug("Request to get Discipline : {}", id);
        return disciplineRepository.findById(id).map(disciplineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discipline : {}", id);
        disciplineRepository.deleteById(id);
    }
}
