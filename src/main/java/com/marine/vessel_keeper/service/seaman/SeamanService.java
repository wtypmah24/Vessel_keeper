package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.SeamanRequestDto;
import com.marine.vessel_keeper.dto.response.SeamanResponseDto;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.entity.vessel.VesselType;
import com.marine.vessel_keeper.exception.SeamanCertificateException;
import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.mapper.SeamanMapper;
import com.marine.vessel_keeper.repository.SeamanRepository;
import com.marine.vessel_keeper.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeamanService {
    private final SeamanMapper seamanMapper;
    private final SeamanRepository seamanRepository;
    private final VesselRepository vesselRepository;
    private final RecordOfServiceService recordService;

    @Autowired
    public SeamanService(SeamanMapper seamanMapper,
                         SeamanRepository seamanRepository,
                         VesselRepository vesselRepository, RecordOfServiceService recordService) {
        this.seamanMapper = seamanMapper;
        this.seamanRepository = seamanRepository;
        this.vesselRepository = vesselRepository;
        this.recordService = recordService;
    }

    @Transactional
    public List<SeamanResponseDto> getAllSeamen() {
        return seamanRepository.findAll().stream().map(seamanMapper::seamanToSeamanResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public SeamanResponseDto addSeamanToLaborPool(SeamanRequestDto candidate) throws SeamanException {
        if (candidate == null) throw new SeamanException("You didn't provide seaman candidate!");
        return seamanMapper.seamanToSeamanResponseDto(seamanRepository.save(seamanMapper.seamanRequestDtoToSeaman(candidate)));
    }

    @Transactional
    public void removeSeamanFromLaborPool(long seamanId) throws SeamanException {
        seamanRepository.delete(seamanRepository.findById(seamanId).orElseThrow(() -> new SeamanException("There is no seaman with id: " + seamanId)));
    }

    @Transactional
    public Set<SeamanResponseDto> hireSeaman(long seamanId, long vesselId) throws SeamanException, SeamanCertificateException {
        Seaman seaman = seamanRepository.findById(seamanId).orElseThrow();
        Vessel vessel = vesselRepository.findByImoNumber(vesselId).orElseThrow();
        certificateCheck(seaman);
        return seamanMapper.seamenToSeamenResponseDtos(vessel.addSeamanToCrew(seaman));
    }

    @Transactional
    public Set<SeamanResponseDto> signOffSeaman(long seamanId, long imoNumber, String comment) throws SeamanException, VesselException {
        Seaman seaman = seamanRepository.findById(seamanId).orElseThrow(() -> new SeamanException("There is no seaman with id: " + seamanId));
        Vessel vessel = vesselRepository.findByImoNumber(imoNumber).orElseThrow(() -> new VesselException("There is no vessel with IMO number: " + imoNumber));

        recordService.addRecordOfService(seaman, vessel, comment);

        vessel.signOffSeaman(seaman);
        return seamanMapper.seamenToSeamenResponseDtos(vessel.getCrew());
    }

    @Transactional
    public Set<SeamanResponseDto> changeCrew(long signOnId, long signOffId, long vesselId, String comment) throws SeamanException, VesselException, SeamanCertificateException {
        signOffSeaman(signOffId, vesselId, comment);
        return hireSeaman(signOnId, vesselId);
    }

    @Transactional
    public Set<SeamanResponseDto> findApplicableSeamenToVessel(long imoNumber) throws VesselException {
        Vessel vessel = vesselRepository.findByImoNumber(imoNumber).orElseThrow(() -> new VesselException("There is no vessel with provided IMO number!"));
        VesselType vesselType = vessel.getVesselType();
        return seamanMapper.seamenToSeamenResponseDtos(seamanRepository.findSeamenByShipType(vesselType));
    }

    private void certificateCheck(Seaman seaman) throws SeamanException, SeamanCertificateException {
        if (!hasCertificate(seaman)) throw new SeamanCertificateException("Candidate has no certificates!");
        if (!isCertificateUpdate(seaman))
            throw new SeamanCertificateException("Candidate's certificates are not up to date!");
        if (seaman.isHasJob()) throw new SeamanException("Candidate is already on a vessel!");
    }

    private boolean hasCertificate(Seaman seaman) {
        return !seaman.getCertificates().isEmpty();
    }

    private boolean isCertificateUpdate(Seaman seaman) {
        return seaman.getCertificates().stream().allMatch(c -> c.getExpireDate().isAfter(LocalDate.now().plusMonths(4)));
    }
}