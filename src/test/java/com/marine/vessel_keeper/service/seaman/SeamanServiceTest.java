package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.SeamanRequestDto;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.seaman.SeamanCertificate;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.exception.WrongCandidateException;
import com.marine.vessel_keeper.mapper.SeamanMapper;
import com.marine.vessel_keeper.repository.SeamanRepository;
import com.marine.vessel_keeper.repository.VesselRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class SeamanServiceTest {
    @Mock
    private SeamanMapper seamanMapper;
    @Mock
    private SeamanRepository seamanRepository;
    @Mock
    private VesselRepository vesselRepository;
    @Mock
    private RecordOfServiceService recordService;
    @InjectMocks
    SeamanService service;
    private final Set<Seaman> laborPool = new HashSet<>();
    private Seaman seaman;
    private Vessel vessel;
    private final String comment = "good";
    private SeamanCertificate certificate;

    @BeforeEach
    void setUp(){
        seaman = new Seaman();
        vessel = new Vessel();
        when(seamanMapper.seamanRequestDtoToSeaman(any(SeamanRequestDto.class))).thenReturn(seaman);
        when(seamanRepository.save(any(Seaman.class))).thenAnswer(invocation -> {
            laborPool.add(seaman);
            return seaman;
        });
        when(seamanRepository.findById(anyLong())).thenReturn(Optional.ofNullable(seaman));
        when(vesselRepository.findByImoNumber(anyLong())).thenReturn(Optional.ofNullable(vessel));
        doAnswer(invocation -> {
            laborPool.remove(seaman);
            return null;
        }).when(seamanRepository).delete(any(Seaman.class));

        certificate = new SeamanCertificate();
        certificate.setExpireDate(LocalDate.of(2024, 12, 12));
    }
    @Test
    void addSeamanToLaborPool() {
        service.addSeamanToLaborPool(mock(SeamanRequestDto.class));
        assertEquals(1, laborPool.size());
    }

    @Test
    void removeSeamanFromLaborPool() {
        service.addSeamanToLaborPool(mock(SeamanRequestDto.class));
        service.removeSeamanFromLaborPool(1L);
        assertTrue(laborPool.isEmpty());
    }

    @Test
    void hireSeamanPositiveCase() throws WrongCandidateException {
        seaman.addCertificate(certificate);
        service.hireSeaman(1L, 1L);
        assertEquals(1, vessel.getCrew().size());
    }

    @Test
    void hireSeamanNegativeCases() throws WrongCandidateException {
        WrongCandidateException exception =  assertThrows(WrongCandidateException.class, ()-> service.hireSeaman(1L, 1L));
        assertEquals("Candidate has no certificates!", exception.getMessage());
        certificate.setExpireDate(LocalDate.of(2024, 3, 20));
        seaman.addCertificate(certificate);
        exception = assertThrows(WrongCandidateException.class, ()-> service.hireSeaman(1L, 1L));
        assertEquals("Candidate's certificates are not up to date!", exception.getMessage());
        seaman.setHasJob(true);
        certificate.setExpireDate(LocalDate.of(2024, 12, 20));
        exception = assertThrows(WrongCandidateException.class, ()-> service.hireSeaman(1L, 1L));
        assertEquals("Candidate is already on a vessel!", exception.getMessage());
    }

    @Test
    void signOffSeaman() throws WrongCandidateException {
        seaman.addCertificate(certificate);
        service.hireSeaman(1L, 1L);
        service.signOffSeaman(1L, 1L, comment);
        assertTrue(vessel.getCrew().isEmpty());
    }
    @Test
    void findApplicableSeamenToVessel() {

    }
}