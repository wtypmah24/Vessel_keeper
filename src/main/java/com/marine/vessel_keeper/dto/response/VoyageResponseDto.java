package com.marine.vessel_keeper.dto.response;

import java.time.LocalDate;

public record VoyageResponseDto(long id,
                                String portOfLoading,
                                String portOfDischarging,
                                LocalDate startDate,
                                LocalDate endDate) {
}
