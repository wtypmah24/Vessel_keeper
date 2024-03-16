package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.VesselRequestDto;
import com.marine.vessel_keeper.dto.response.VesselResponseDto;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface VesselMapper {
    Vessel vesselRequestDtoToVessel(VesselRequestDto vesselCandidate);
    VesselResponseDto vesselToVesselResponseDto(Vessel vessel);
    Set<VesselResponseDto> vesselsToVesselResponseDtos(List<Vessel> vessels);
}
