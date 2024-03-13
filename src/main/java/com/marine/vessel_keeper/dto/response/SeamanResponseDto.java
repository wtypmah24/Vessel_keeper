package com.marine.vessel_keeper.dto.response;

import java.util.Set;

public record SeamanResponseDto(long id, String name, String rank, Set<CertificateResponseDto> certificates) {
}
