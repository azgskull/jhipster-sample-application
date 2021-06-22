package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ModePaiement;
import com.mycompany.myapp.repository.ModePaiementRepository;
import com.mycompany.myapp.service.ModePaiementService;
import com.mycompany.myapp.service.dto.ModePaiementDTO;
import com.mycompany.myapp.service.mapper.ModePaiementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ModePaiement}.
 */
@Service
@Transactional
public class ModePaiementServiceImpl implements ModePaiementService {

    private final Logger log = LoggerFactory.getLogger(ModePaiementServiceImpl.class);

    private final ModePaiementRepository modePaiementRepository;

    private final ModePaiementMapper modePaiementMapper;

    public ModePaiementServiceImpl(ModePaiementRepository modePaiementRepository, ModePaiementMapper modePaiementMapper) {
        this.modePaiementRepository = modePaiementRepository;
        this.modePaiementMapper = modePaiementMapper;
    }

    @Override
    public ModePaiementDTO save(ModePaiementDTO modePaiementDTO) {
        log.debug("Request to save ModePaiement : {}", modePaiementDTO);
        ModePaiement modePaiement = modePaiementMapper.toEntity(modePaiementDTO);
        modePaiement = modePaiementRepository.save(modePaiement);
        return modePaiementMapper.toDto(modePaiement);
    }

    @Override
    public Optional<ModePaiementDTO> partialUpdate(ModePaiementDTO modePaiementDTO) {
        log.debug("Request to partially update ModePaiement : {}", modePaiementDTO);

        return modePaiementRepository
            .findById(modePaiementDTO.getId())
            .map(
                existingModePaiement -> {
                    modePaiementMapper.partialUpdate(existingModePaiement, modePaiementDTO);
                    return existingModePaiement;
                }
            )
            .map(modePaiementRepository::save)
            .map(modePaiementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModePaiementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModePaiements");
        return modePaiementRepository.findAll(pageable).map(modePaiementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModePaiementDTO> findOne(Long id) {
        log.debug("Request to get ModePaiement : {}", id);
        return modePaiementRepository.findById(id).map(modePaiementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModePaiement : {}", id);
        modePaiementRepository.deleteById(id);
    }
}
