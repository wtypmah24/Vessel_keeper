package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.SeamanRequestDto;
import com.marine.vessel_keeper.dto.response.SeamanResponseDto;
import com.marine.vessel_keeper.entity.seaman.Rank;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SeamanMapper {
    @Mapping(target = "hasJob", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "recordOfServices", ignore = true)
    Seaman seamanRequestDtoToSeaman(SeamanRequestDto seamanCandidate);

    SeamanResponseDto seamanToSeamanResponseDto(Seaman seaman);

    @Mapping(target = "recordOfServices", ignore = true)
    Set<SeamanResponseDto> seamenToSeamenResponseDtos(Set<Seaman> seamanSet);

    default Rank mapRole(String rank) {
        return Rank.valueOf(rank.toUpperCase());
    }
}
