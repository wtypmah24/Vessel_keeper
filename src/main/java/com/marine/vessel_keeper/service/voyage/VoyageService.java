package com.marine.vessel_keeper.service.voyage;

import com.marine.vessel_keeper.dto.request.VoyageRequestDto;
import com.marine.vessel_keeper.dto.response.VoyageResponseDto;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import com.marine.vessel_keeper.mapper.VoyageMapper;
import com.marine.vessel_keeper.repository.VesselRepository;
import com.marine.vessel_keeper.repository.VoyageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoyageService {
    private final VoyageMapper mapper;
    private final VoyageRepository voyageRepository;
    private final VesselRepository vesselRepository;


    public VoyageService(VoyageMapper mapper, VoyageRepository voyageRepository, VesselRepository vesselRepository) {
        this.mapper = mapper;
        this.voyageRepository = voyageRepository;
        this.vesselRepository = vesselRepository;
    }

    @Transactional
    public VoyageResponseDto addNewVoyage(VoyageRequestDto candidate) {
        return mapper.voyageToVoyageResponseDto(voyageRepository.save(mapper.voyageRequestDtoToVoyage(candidate)));
    }

    @Transactional
    public VoyageResponseDto assignVoyageToVessel(long voyageId, long vesselId) {
        Voyage voyage = voyageRepository.findById(voyageId).orElseThrow();
        Vessel vessel = vesselRepository.findByImoNumber(vesselId).orElseThrow();

        voyage.setVessel(vessel);
        vessel.setVoyage(voyage);

        return mapper.voyageToVoyageResponseDto(vessel.getVoyage());
    }

    @Transactional
    public List<VoyageResponseDto> getAllVoyages() {
        return mapper.voyagesToVoyageResponseDtos(voyageRepository.findAll());
    }

    @Transactional
    public List<VoyageResponseDto> getAllAvailableVoyages() {
        return mapper.voyagesToVoyageResponseDtos(voyageRepository.findByVesselIsNull());
    }

    @Transactional
    public List<VoyageResponseDto> getVoyagesByDischargingPortAndEndDate(String dischargingPort, LocalDate endDate) {
        return mapper.voyagesToVoyageResponseDtos(
                voyageRepository.findByPortOfDischargingAndEndDateAndVesselIsNotNull(dischargingPort, endDate)
        );
    }

    @Transactional
    public List<VoyageResponseDto> getVoyagesByLoadingPortAndStartDate(String loadingPort, LocalDate startDate) {
        return mapper.voyagesToVoyageResponseDtos(
                voyageRepository.findByPortOfLoadingAndStartDateAndVesselIsNull(loadingPort, startDate)
        );
    }
}