package com.marine.vessel_keeper.service.voyage;

import com.marine.vessel_keeper.dto.request.VoyageRequestDto;
import com.marine.vessel_keeper.dto.response.VoyageResponseDto;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.exception.VoyageException;
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
    public VoyageResponseDto addNewVoyage(VoyageRequestDto candidate) throws VoyageException {
        voyageCandidateCheck(candidate);
        return mapper.voyageToVoyageResponseDto(voyageRepository.save(mapper.voyageRequestDtoToVoyage(candidate)));
    }

    @Transactional
    public VoyageResponseDto assignVoyageToVessel(long voyageId, long imoNumber) throws VoyageException, VesselException {
        Voyage voyage = voyageRepository.findById(voyageId).orElseThrow(() -> new VoyageException("There is ni voyage with id: " + voyageId));
        Vessel vessel = vesselRepository.findByImoNumber(imoNumber).orElseThrow(() -> new VesselException("There is no vessel with IMO number: " + imoNumber));

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

    private void voyageCandidateCheck(VoyageRequestDto candidate) throws VoyageException {
        if (candidate == null) throw new VoyageException("You didn't provide voyage");
        if (candidate.portOfDischarging().isBlank())
            throw new VoyageException("You didn't provide port of discharging");
        if (candidate.portOfLoading().isBlank()) throw new VoyageException("You didn't provide port of loading");
        if (candidate.startDate().isBefore(LocalDate.now()))
            throw new VoyageException("Provided voyage is expired. Start date is " + candidate.startDate());
        if (candidate.endDate().isBefore(LocalDate.now()))
            throw new VoyageException("Provided voyage is expired. End date is " + candidate.endDate());
        if (candidate.endDate().isBefore(candidate.startDate()))
            throw new VoyageException("End date must be after start date ");
    }
}