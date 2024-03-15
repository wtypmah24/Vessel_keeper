package com.marine.vessel_keeper.service.vessel;

import com.marine.vessel_keeper.dto.response.VesselResponseDto;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import com.marine.vessel_keeper.mapper.VesselMapper;
import com.marine.vessel_keeper.repository.VesselRepository;
import com.marine.vessel_keeper.repository.VoyageRepository;
import com.marine.vessel_keeper.service.voyage.VoyageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VesselService {
    private final VesselRepository vesselRepository;
    private final VesselMapper vesselMapper;
    private final VoyageService voyageService;
    private final VoyageRepository voyageRepository;

    public VesselService(VesselRepository vesselRepository, VesselMapper vesselMapper, VoyageService voyageService, VoyageRepository voyageRepository) {
        this.vesselRepository = vesselRepository;
        this.vesselMapper = vesselMapper;
        this.voyageService = voyageService;
        this.voyageRepository = voyageRepository;
    }

    public List<VesselResponseDto> findApplicableVesselToVoyage(long voyageId){
        Voyage voyage = voyageRepository.findById(voyageId).orElseThrow();
        return vesselMapper.vesselsToVesselResponseDtos(
                vesselRepository.findVesselsByVoyage_PortOfDischargingAndVoyage_EndDate(
                        voyage.getPortOfDischarging(),
                        voyage.getEndDate())
        );
    }

}
