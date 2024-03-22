package com.marine.vessel_keeper.service.vessel;

import com.marine.vessel_keeper.dto.request.VesselRequestDto;
import com.marine.vessel_keeper.dto.response.VesselResponseDto;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.mapper.VesselMapper;
import com.marine.vessel_keeper.repository.VesselRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VesselServiceTest {
    @InjectMocks
    private VesselService service;
    @Mock(lenient = true)
    private VesselMapper mapper;
    @Mock(lenient = true)
    private VesselRepository repository;
    private final List<Vessel> vessels = new ArrayList<>();
    private final Set<VesselResponseDto> responseDtos = new HashSet<>();
    private Vessel vessel;

    @BeforeEach
    void setUp() {
        vessel = new Vessel();
        doAnswer(invocation -> {
            vessels.add(vessel);
            return vessel;
        }).when(repository).save(any(Vessel.class));
        when(mapper.vesselRequestDtoToVessel(any(VesselRequestDto.class))).thenReturn(vessel);
        when(repository.findByImoNumber(anyLong())).thenReturn(Optional.ofNullable(vessel));
        doAnswer(invocation -> {
            vessels.remove(vessel);
            return null;
        }).when(repository).delete(any(Vessel.class));

        when(repository.findAll()).thenReturn(vessels);
    }

    @Test
    void addVessel() {
        service.addVessel(mock(VesselRequestDto.class));
        assertEquals(1, vessels.size());
    }

    @Test
    void deleteVessel() throws VesselException {
        service.addVessel(mock(VesselRequestDto.class));
        service.deleteVessel(1L);
        assertTrue(vessels.isEmpty());
    }

    @Test
    void getAllVessels() {
        service.addVessel(mock(VesselRequestDto.class));
        //service.getAllVessels()
    }

    @Test
    void findApplicableVesselToVoyage() {
    }
}