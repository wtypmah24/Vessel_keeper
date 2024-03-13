package com.marine.vessel_keeper.dto.response;

import java.time.LocalDate;

public record CertificateResponseDto(long id, String name, LocalDate expireDate) {
}
