package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.seaman.SeamanCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<SeamanCertificate, Long> {
    Optional<SeamanCertificate> findById(long id);
}
