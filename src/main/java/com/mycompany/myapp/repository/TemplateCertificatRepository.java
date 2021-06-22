package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TemplateCertificat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TemplateCertificat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateCertificatRepository extends JpaRepository<TemplateCertificat, Long> {}
