package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.SeamanRequestDto;
import com.marine.vessel_keeper.dto.response.SeamanResponseDto;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.exception.WrongCandidateException;
import com.marine.vessel_keeper.mapper.SeamanMapper;
import com.marine.vessel_keeper.repository.SeamanRepository;
import com.marine.vessel_keeper.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public SeamanResponseDto addSeamanToLaborPool(SeamanRequestDto candidate) {
        return seamanMapper.seamanToSeamanResponseDto(seamanRepository.save(seamanMapper.seamanRequestDtoToSeaman(candidate)));
    }

    @Transactional
    public void removeSeamanFromLaborPool(long seamanId) {
        seamanRepository.delete(seamanRepository.findById(seamanId).orElseThrow());
    }

    //TODO: Refactor this method
    @Transactional
    public Set<SeamanResponseDto> hireSeaman(long seamanId, long vesselId) throws WrongCandidateException {
        Seaman seaman = seamanRepository.findById(seamanId).orElseThrow();
        Vessel vessel = vesselRepository.findByImoNumber(vesselId).orElseThrow();

        if (!hasCertificate(seaman)) throw new WrongCandidateException("Candidate has no certificates!");
        if (!isCertificateUpdate(seaman)) throw new WrongCandidateException("Candidate's certificates are not up to date!");
        if (seaman.isHasJob()) throw new WrongCandidateException("Candidate is already on a vessel!");

        return vessel.addSeamanToCrew(seaman).stream().map(seamanMapper::seamanToSeamanResponseDto).collect(Collectors.toSet());
    }

    @Transactional
    public Set<SeamanResponseDto> signOffSeaman(long seamanId, long vesselId, String comment) {
        Seaman seaman = seamanRepository.findById(seamanId).orElseThrow();
        Vessel vessel = vesselRepository.findByImoNumber(vesselId).orElseThrow();

        recordService.addRecordOfService(seaman, vessel, comment);

        vessel.signOffSeaman(seaman);

        return vessel.getCrew().stream().map(seamanMapper::seamanToSeamanResponseDto).collect(Collectors.toSet());
    }
    @Transactional
    public Set<SeamanResponseDto> changeCrew(long signOnId, long signOffId, long vesselId, String comment) throws WrongCandidateException {
        signOffSeaman(signOffId, vesselId, comment);
        return hireSeaman(signOnId, vesselId);
    }

    private boolean hasCertificate(Seaman seaman) {
        return !seaman.getCertificates().isEmpty();
    }

    private boolean isCertificateUpdate(Seaman seaman) {
        return seaman.getCertificates().stream().allMatch(c -> c.getExpireDate().isAfter(LocalDate.now().plusMonths(4)));
    }
}