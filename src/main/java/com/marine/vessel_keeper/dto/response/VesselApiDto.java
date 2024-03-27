package com.marine.vessel_keeper.dto.response;

import java.time.LocalDate;

public record VesselApiDto(long imoNumber,
                           String name,
                           String departure,
                           String destination,
                           LocalDate arrivalDate) {
}
