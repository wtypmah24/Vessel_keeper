package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.RecordOfServiceRequestDto;
import com.marine.vessel_keeper.dto.response.RecordOfServiceResponseDto;
import com.marine.vessel_keeper.entity.seaman.RecordOfService;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.exception.SeamanException;
import com.marine.vessel_keeper.exception.VesselException;
import com.marine.vessel_keeper.mapper.RecordOfServiceMapper;
import com.marine.vessel_keeper.repository.RecordOfServiceRepository;
import com.marine.vessel_keeper.repository.SeamanRepository;
import com.marine.vessel_keeper.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordOfServiceService {

    private final RecordOfServiceRepository repository;
    private final RecordOfServiceMapper mapper;
    private final SeamanRepository seamanRepository;
    private final VesselRepository vesselRepository;

    @Autowired
    public RecordOfServiceService(RecordOfServiceRepository repository, RecordOfServiceMapper mapper, SeamanRepository seamanRepository, VesselRepository vesselRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.seamanRepository = seamanRepository;
        this.vesselRepository = vesselRepository;
    }

    @Transactional
    public void addRecordOfService(Seaman seaman, Vessel vessel, String comment) {
        RecordOfServiceRequestDto candidate = createRequestDto(seaman, vessel, comment);
        RecordOfService record = mapper.recordOfServiceRequestDtoToRecordOfService(candidate);
        seaman.addServiceRecord(record);
        repository.save(record);
    }

    @Transactional
    public void addServiceRecordManually(long seamanId, long imoNumber, String comment) throws VesselException, SeamanException {
        Vessel vessel = vesselRepository.findByImoNumber(imoNumber).orElseThrow(() -> new VesselException("There is no vessel with IMO number: " + imoNumber));
        Seaman seaman = seamanRepository.findById(seamanId).orElseThrow(() -> new SeamanException("There is no seaman with id: " + seamanId));
        addRecordOfService(seaman, vessel, comment);
    }

    private RecordOfServiceRequestDto createRequestDto(Seaman seaman, Vessel vessel, String comment) {
        return new RecordOfServiceRequestDto(seaman, vessel, comment);
    }
}