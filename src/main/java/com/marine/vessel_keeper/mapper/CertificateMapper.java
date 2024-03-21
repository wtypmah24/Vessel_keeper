package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.CertificateRequestDto;
import com.marine.vessel_keeper.dto.response.CertificateResponseDto;
import com.marine.vessel_keeper.entity.seaman.SeamanCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    @Mapping(target = "seaman", ignore = true)
    SeamanCertificate certificateRequestDtoToCertificate(CertificateRequestDto certificateCandidate);
    CertificateResponseDto certificateToCertificateResponseDto(SeamanCertificate certificate);
}