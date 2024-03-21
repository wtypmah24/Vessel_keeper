package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.VoyageRequestDto;
import com.marine.vessel_keeper.dto.response.VoyageResponseDto;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoyageMapper {
    @Mapping(target = "vessel", ignore = true)
    Voyage voyageRequestDtoToVoyage(VoyageRequestDto candidate);

    VoyageResponseDto voyageToVoyageResponseDto(Voyage voyage);

    @Mapping(target = "vessel", ignore = true)
    List<VoyageResponseDto> voyagesToVoyageResponseDtos(List<Voyage> voyages);
}
