package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.VesselRequestDto;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VesselMapper {
    Vessel vesselDtoToVessel(VesselRequestDto vesselCandidate);
    VesselRequestDto vesselToVesselDto(Vessel vessel);
}
