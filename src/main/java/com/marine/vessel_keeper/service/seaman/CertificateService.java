package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.CertificateRequestDto;
import com.marine.vessel_keeper.dto.response.CertificateResponseDto;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.seaman.SeamanCertificate;
import com.marine.vessel_keeper.mapper.CertificateMapper;
import com.marine.vessel_keeper.mapper.SeamanMapper;
import com.marine.vessel_keeper.repository.CertificateRepository;
import com.marine.vessel_keeper.repository.SeamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.cert.Certificate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    private final CertificateMapper certificateMapper;
    private final CertificateRepository certificateRepository;
    private final SeamanRepository seamanRepository;

    @Autowired
    public CertificateService(CertificateMapper certificateMapper, CertificateRepository certificateRepository, SeamanRepository seamanRepository) {
        this.certificateMapper = certificateMapper;
        this.certificateRepository = certificateRepository;
        this.seamanRepository = seamanRepository;
    }

    @Transactional
    public Set<CertificateResponseDto> addCertificateToSeaman(CertificateRequestDto candidate, long seamanId){
        Seaman seaman = seamanRepository.findById(seamanId).orElseThrow();
        return seaman.addCertificate(addCertificateToDb(candidate))
                .stream()
                .map(certificateMapper::certificateToCertificateResponseDto)
                .collect(Collectors.toSet());
    }
    @Transactional
    public Set<CertificateResponseDto> deleteCertificate(long id){
        SeamanCertificate certificate = certificateRepository.findById(id).orElseThrow();
        Seaman seaman = certificate.getSeaman();
        removeCertificateFromDb(certificate);
        return seaman.removeCertificate(certificate)
                .stream()
                .map(certificateMapper::certificateToCertificateResponseDto)
                .collect(Collectors.toSet());
    }


    private SeamanCertificate addCertificateToDb(CertificateRequestDto candidate) {
        return certificateRepository.save(certificateMapper.certificateRequestDtoToCertificate(candidate));
    }

    private void removeCertificateFromDb(SeamanCertificate certificate){
        certificateRepository.delete(certificate);
    }
}
