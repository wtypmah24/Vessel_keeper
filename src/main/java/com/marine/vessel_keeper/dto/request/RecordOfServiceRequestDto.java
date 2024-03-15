package com.marine.vessel_keeper.dto.request;

import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.Vessel;

public record RecordOfServiceRequestDto(Seaman seaman, Vessel vessel, String comment) {
}
