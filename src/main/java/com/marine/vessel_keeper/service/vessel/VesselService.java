package com.marine.vessel_keeper.service.vessel;

import com.marine.vessel_keeper.dto.request.VesselRequestDto;
import com.marine.vessel_keeper.dto.response.VesselResponseDto;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.exception.VoyageException;
import com.marine.vessel_keeper.mapper.VesselMapper;
import com.marine.vessel_keeper.repository.VesselRepository;
import com.marine.vessel_keeper.repository.VoyageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class VesselService {
    private final VesselRepository vesselRepository;
    private final VesselMapper vesselMapper;
    private final VoyageRepository voyageRepository;

    public VesselService(VesselRepository vesselRepository,
                         VesselMapper vesselMapper,
                         VoyageRepository voyageRepository) {
        this.vesselRepository = vesselRepository;
        this.vesselMapper = vesselMapper;
        this.voyageRepository = voyageRepository;
    }

    @Transactional
    public VesselResponseDto addVessel(VesselRequestDto candidate) throws VesselException {
        checkVesselCandidate(candidate);
        return vesselMapper
                .vesselToVesselResponseDto(
                        vesselRepository.save(vesselMapper.vesselRequestDtoToVessel(candidate)));
    }

    @Transactional
    public void deleteVessel(long imoNumber) throws VesselException {
        Vessel vessel = vesselRepository.findByImoNumber(imoNumber).orElseThrow(() -> new VesselException("There is mo vessel with IMO number: " + imoNumber));
        vesselRepository.delete(vessel);
    }

    public Set<VesselResponseDto> getAllVessels() {
        return vesselMapper.vesselsToVesselResponseDtos(vesselRepository.findAll());
    }

    @Transactional
    public Set<VesselResponseDto> findApplicableVesselToVoyage(long voyageId) throws VoyageException {
        Voyage voyage = voyageRepository.findById(voyageId).orElseThrow(() -> new VoyageException("There is no voyage with id: " + voyageId));
        return vesselMapper.vesselsToVesselResponseDtos(
                vesselRepository.findVesselsByVoyage_PortOfDischargingAndVoyage_EndDate(
                        voyage.getPortOfDischarging(),
                        voyage.getEndDate())
        );
    }
    private void checkVesselCandidate(VesselRequestDto candidate) throws VesselException {
        if (candidate == null) throw new VesselException("You didn't provide vessel.");
        if (candidate.imoNumber() == null) throw new VesselException("You didn't provide IMO number.");
        if (candidate.name().isBlank()) throw new VesselException("You didn't provide vessel name.");
    }
}
