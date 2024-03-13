package com.marine.vessel_keeper.dto.request;

import java.time.LocalDate;

public record CertificateRequestDto(String name, LocalDate expireDate) {
}
