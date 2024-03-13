package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.SeamanRequestDto;
import com.marine.vessel_keeper.dto.response.SeamanResponseDto;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SeamanMapper {
    Seaman seamanRequestDtoToSeaman(SeamanRequestDto seamanCandidate);
    SeamanRequestDto seamanToSeamanRequestDto(Seaman seaman);
    SeamanResponseDto seamanToSeamanResponseDto(Seaman seaman);
}
