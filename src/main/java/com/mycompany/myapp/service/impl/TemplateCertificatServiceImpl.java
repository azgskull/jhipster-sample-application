package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TemplateCertificat;
import com.mycompany.myapp.repository.TemplateCertificatRepository;
import com.mycompany.myapp.service.TemplateCertificatService;
import com.mycompany.myapp.service.dto.TemplateCertificatDTO;
import com.mycompany.myapp.service.mapper.TemplateCertificatMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TemplateCertificat}.
 */
@Service
@Transactional
public class TemplateCertificatServiceImpl implements TemplateCertificatService {

    private final Logger log = LoggerFactory.getLogger(TemplateCertificatServiceImpl.class);

    private final TemplateCertificatRepository templateCertificatRepository;

    private final TemplateCertificatMapper templateCertificatMapper;

    public TemplateCertificatServiceImpl(
        TemplateCertificatRepository templateCertificatRepository,
        TemplateCertificatMapper templateCertificatMapper
    ) {
        this.templateCertificatRepository = templateCertificatRepository;
        this.templateCertificatMapper = templateCertificatMapper;
    }

    @Override
    public TemplateCertificatDTO save(TemplateCertificatDTO templateCertificatDTO) {
        log.debug("Request to save TemplateCertificat : {}", templateCertificatDTO);
        TemplateCertificat templateCertificat = templateCertificatMapper.toEntity(templateCertificatDTO);
        templateCertificat = templateCertificatRepository.save(templateCertificat);
        return templateCertificatMapper.toDto(templateCertificat);
    }

    @Override
    public Optional<TemplateCertificatDTO> partialUpdate(TemplateCertificatDTO templateCertificatDTO) {
        log.debug("Request to partially update TemplateCertificat : {}", templateCertificatDTO);

        return templateCertificatRepository
            .findById(templateCertificatDTO.getId())
            .map(
                existingTemplateCertificat -> {
                    templateCertificatMapper.partialUpdate(existingTemplateCertificat, templateCertificatDTO);
                    return existingTemplateCertificat;
                }
            )
            .map(templateCertificatRepository::save)
            .map(templateCertificatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateCertificatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateCertificats");
        return templateCertificatRepository.findAll(pageable).map(templateCertificatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TemplateCertificatDTO> findOne(Long id) {
        log.debug("Request to get TemplateCertificat : {}", id);
        return templateCertificatRepository.findById(id).map(templateCertificatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemplateCertificat : {}", id);
        templateCertificatRepository.deleteById(id);
    }
}
