package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.CertificateRequestDto;
import com.marine.vessel_keeper.dto.response.CertificateResponseDto;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.seaman.SeamanCertificate;
import com.marine.vessel_keeper.mapper.CertificateMapper;
import com.marine.vessel_keeper.repository.CertificateRepository;
import com.marine.vessel_keeper.repository.SeamanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class CertificateServiceTest {
    @Mock
    private CertificateMapper certificateMapper;
    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private SeamanRepository seamanRepository;
    @InjectMocks
    private CertificateService service;

    private Seaman seaman;
    private SeamanCertificate seamanCertificate;
    private final Set<SeamanCertificate> certificates = new HashSet<>();
    @BeforeEach
    void setUp(){
        seaman = new Seaman();
        seamanCertificate = new SeamanCertificate();
        seaman.setCertificates(certificates);
        seamanCertificate.setSeaman(seaman);
    }

    @Test
    void addCertificateToSeaman() {
        CertificateRequestDto candidate = mock(CertificateRequestDto.class);

        when(seamanRepository.findById(anyLong())).thenReturn(Optional.of(seaman));
        when(certificateMapper.certificateRequestDtoToCertificate(any(CertificateRequestDto.class))).thenReturn(seamanCertificate);
        when(certificateMapper.certificateToCertificateResponseDto(any(SeamanCertificate.class))).thenReturn(mock(CertificateResponseDto.class));

        when(certificateRepository.save(any(SeamanCertificate.class))).thenAnswer(invocation -> {
            certificates.add(seamanCertificate);
            return seamanCertificate;
        });
        service.addCertificateToSeaman(candidate, 1L);
        System.out.println("seaman certificate belongs to  " + seamanCertificate.getSeaman());
        assertEquals(1, seaman.getCertificates().size());
    }

    @Test
    void deleteCertificate() {
        certificates.add(seamanCertificate);
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(seamanCertificate));
        service.deleteCertificate(1L);
        assertTrue(seaman.getCertificates().isEmpty());
    }
}