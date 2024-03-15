package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.RecordOfServiceRequestDto;
import com.marine.vessel_keeper.dto.response.RecordOfServiceResponseDto;
import com.marine.vessel_keeper.entity.seaman.RecordOfService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecordOfServiceMapper {
    RecordOfService recordOfServiceRequestDtoToRecordOfService(RecordOfServiceRequestDto candidate);
    RecordOfServiceResponseDto recordOfServiceToRecordOfServiceResponseDto(RecordOfService record);
}
