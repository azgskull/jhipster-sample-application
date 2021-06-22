package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TypeCertificat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeCertificat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCertificatRepository extends JpaRepository<TypeCertificat, Long> {}
