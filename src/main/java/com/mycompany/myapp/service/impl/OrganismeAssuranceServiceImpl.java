package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.OrganismeAssurance;
import com.mycompany.myapp.repository.OrganismeAssuranceRepository;
import com.mycompany.myapp.service.OrganismeAssuranceService;
import com.mycompany.myapp.service.dto.OrganismeAssuranceDTO;
import com.mycompany.myapp.service.mapper.OrganismeAssuranceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrganismeAssurance}.
 */
@Service
@Transactional
public class OrganismeAssuranceServiceImpl implements OrganismeAssuranceService {

    private final Logger log = LoggerFactory.getLogger(OrganismeAssuranceServiceImpl.class);

    private final OrganismeAssuranceRepository organismeAssuranceRepository;

    private final OrganismeAssuranceMapper organismeAssuranceMapper;

    public OrganismeAssuranceServiceImpl(
        OrganismeAssuranceRepository organismeAssuranceRepository,
        OrganismeAssuranceMapper organismeAssuranceMapper
    ) {
        this.organismeAssuranceRepository = organismeAssuranceRepository;
        this.organismeAssuranceMapper = organismeAssuranceMapper;
    }

    @Override
    public OrganismeAssuranceDTO save(OrganismeAssuranceDTO organismeAssuranceDTO) {
        log.debug("Request to save OrganismeAssurance : {}", organismeAssuranceDTO);
        OrganismeAssurance organismeAssurance = organismeAssuranceMapper.toEntity(organismeAssuranceDTO);
        organismeAssurance = organismeAssuranceRepository.save(organismeAssurance);
        return organismeAssuranceMapper.toDto(organismeAssurance);
    }

    @Override
    public Optional<OrganismeAssuranceDTO> partialUpdate(OrganismeAssuranceDTO organismeAssuranceDTO) {
        log.debug("Request to partially update OrganismeAssurance : {}", organismeAssuranceDTO);

        return organismeAssuranceRepository
            .findById(organismeAssuranceDTO.getId())
            .map(
                existingOrganismeAssurance -> {
                    organismeAssuranceMapper.partialUpdate(existingOrganismeAssurance, organismeAssuranceDTO);
                    return existingOrganismeAssurance;
                }
            )
            .map(organismeAssuranceRepository::save)
            .map(organismeAssuranceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganismeAssuranceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganismeAssurances");
        return organismeAssuranceRepository.findAll(pageable).map(organismeAssuranceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganismeAssuranceDTO> findOne(Long id) {
        log.debug("Request to get OrganismeAssurance : {}", id);
        return organismeAssuranceRepository.findById(id).map(organismeAssuranceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganismeAssurance : {}", id);
        organismeAssuranceRepository.deleteById(id);
    }
}
