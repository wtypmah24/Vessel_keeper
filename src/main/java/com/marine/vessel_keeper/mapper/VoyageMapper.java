package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.VoyageRequestDto;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoyageMapper {
    Voyage voyageDtoToVoyage(Voyage voyageCandidate);
    VoyageRequestDto voyageToVoyageDto(Voyage voyage);
}
