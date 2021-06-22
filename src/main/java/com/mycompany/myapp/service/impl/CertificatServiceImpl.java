package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Certificat;
import com.mycompany.myapp.repository.CertificatRepository;
import com.mycompany.myapp.service.CertificatService;
import com.mycompany.myapp.service.dto.CertificatDTO;
import com.mycompany.myapp.service.mapper.CertificatMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Certificat}.
 */
@Service
@Transactional
public class CertificatServiceImpl implements CertificatService {

    private final Logger log = LoggerFactory.getLogger(CertificatServiceImpl.class);

    private final CertificatRepository certificatRepository;

    private final CertificatMapper certificatMapper;

    public CertificatServiceImpl(CertificatRepository certificatRepository, CertificatMapper certificatMapper) {
        this.certificatRepository = certificatRepository;
        this.certificatMapper = certificatMapper;
    }

    @Override
    public CertificatDTO save(CertificatDTO certificatDTO) {
        log.debug("Request to save Certificat : {}", certificatDTO);
        Certificat certificat = certificatMapper.toEntity(certificatDTO);
        certificat = certificatRepository.save(certificat);
        return certificatMapper.toDto(certificat);
    }

    @Override
    public Optional<CertificatDTO> partialUpdate(CertificatDTO certificatDTO) {
        log.debug("Request to partially update Certificat : {}", certificatDTO);

        return certificatRepository
            .findById(certificatDTO.getId())
            .map(
                existingCertificat -> {
                    certificatMapper.partialUpdate(existingCertificat, certificatDTO);
                    return existingCertificat;
                }
            )
            .map(certificatRepository::save)
            .map(certificatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CertificatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Certificats");
        return certificatRepository.findAll(pageable).map(certificatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CertificatDTO> findOne(Long id) {
        log.debug("Request to get Certificat : {}", id);
        return certificatRepository.findById(id).map(certificatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Certificat : {}", id);
        certificatRepository.deleteById(id);
    }
}
