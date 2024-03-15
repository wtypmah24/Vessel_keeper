package com.marine.vessel_keeper.dto.response;

import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.Vessel;

public record RecordOfServiceResponseDto(long id, Seaman seaman, Vessel vessel, String comment) {
}
