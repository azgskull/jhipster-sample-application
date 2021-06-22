package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TypeCertificat;
import com.mycompany.myapp.repository.TypeCertificatRepository;
import com.mycompany.myapp.service.TypeCertificatService;
import com.mycompany.myapp.service.dto.TypeCertificatDTO;
import com.mycompany.myapp.service.mapper.TypeCertificatMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeCertificat}.
 */
@Service
@Transactional
public class TypeCertificatServiceImpl implements TypeCertificatService {

    private final Logger log = LoggerFactory.getLogger(TypeCertificatServiceImpl.class);

    private final TypeCertificatRepository typeCertificatRepository;

    private final TypeCertificatMapper typeCertificatMapper;

    public TypeCertificatServiceImpl(TypeCertificatRepository typeCertificatRepository, TypeCertificatMapper typeCertificatMapper) {
        this.typeCertificatRepository = typeCertificatRepository;
        this.typeCertificatMapper = typeCertificatMapper;
    }

    @Override
    public TypeCertificatDTO save(TypeCertificatDTO typeCertificatDTO) {
        log.debug("Request to save TypeCertificat : {}", typeCertificatDTO);
        TypeCertificat typeCertificat = typeCertificatMapper.toEntity(typeCertificatDTO);
        typeCertificat = typeCertificatRepository.save(typeCertificat);
        return typeCertificatMapper.toDto(typeCertificat);
    }

    @Override
    public Optional<TypeCertificatDTO> partialUpdate(TypeCertificatDTO typeCertificatDTO) {
        log.debug("Request to partially update TypeCertificat : {}", typeCertificatDTO);

        return typeCertificatRepository
            .findById(typeCertificatDTO.getId())
            .map(
                existingTypeCertificat -> {
                    typeCertificatMapper.partialUpdate(existingTypeCertificat, typeCertificatDTO);
                    return existingTypeCertificat;
                }
            )
            .map(typeCertificatRepository::save)
            .map(typeCertificatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeCertificatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeCertificats");
        return typeCertificatRepository.findAll(pageable).map(typeCertificatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeCertificatDTO> findOne(Long id) {
        log.debug("Request to get TypeCertificat : {}", id);
        return typeCertificatRepository.findById(id).map(typeCertificatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeCertificat : {}", id);
        typeCertificatRepository.deleteById(id);
    }
}
