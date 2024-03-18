package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.RecordOfServiceRequestDto;
import com.marine.vessel_keeper.entity.seaman.RecordOfService;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.seaman.SeamanCertificate;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.mapper.RecordOfServiceMapper;
import com.marine.vessel_keeper.repository.RecordOfServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordOfServiceServiceTest {
    @Mock
    private RecordOfServiceRepository repository;
    @Mock
    private RecordOfServiceMapper mapper;
    @InjectMocks
    private RecordOfServiceService service;
    private Seaman seaman;
    private Vessel vesel;
    private String comment;
    private RecordOfService recordOfService;
    private final Set<RecordOfService> records = new HashSet<>();

    @BeforeEach
    void setUp() {
        seaman = new Seaman();
        vesel = new Vessel();
        recordOfService = new RecordOfService();
        comment = "Good";
    }

    @Test
    void addRecordOfService() {
        when(mapper.recordOfServiceRequestDtoToRecordOfService(any(RecordOfServiceRequestDto.class)))
                .thenReturn(recordOfService);

        when(repository.save(any(RecordOfService.class))).thenAnswer(invocation -> {
            records.add(recordOfService);
            return recordOfService;
        });

        service.addRecordOfService(seaman, vesel, comment);

        assertEquals(1, seaman.getRecordOfServices().size());
    }
}