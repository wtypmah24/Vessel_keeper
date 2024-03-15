package com.marine.vessel_keeper.service.seaman;

import com.marine.vessel_keeper.dto.request.RecordOfServiceRequestDto;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.mapper.RecordOfServiceMapper;
import com.marine.vessel_keeper.repository.RecordOfServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordOfServiceService {

    private final RecordOfServiceRepository repository;
    private final RecordOfServiceMapper mapper;
    @Autowired
    public RecordOfServiceService(RecordOfServiceRepository repository, RecordOfServiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Transactional
    public void addRecordOfService(Seaman seaman, Vessel vessel, String comment){
        RecordOfServiceRequestDto candidate = createRequestDto(seaman, vessel, comment);
        repository.save(mapper.recordOfServiceRequestDtoToRecordOfService(candidate));
    }
    @Transactional
    private RecordOfServiceRequestDto createRequestDto(Seaman seaman, Vessel vessel, String comment){
        return new RecordOfServiceRequestDto(seaman,vessel, comment);
    }
}