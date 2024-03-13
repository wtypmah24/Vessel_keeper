package com.marine.vessel_keeper.dto.request;

import java.time.LocalDate;

public record VoyageRequestDto(String portOfLoading,
                               String portOfDischarging,
                               LocalDate startDate,
                               LocalDate endDate) {
}