package com.marine.vessel_keeper.mapper;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.entity.user.Role;
import com.marine.vessel_keeper.entity.user.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserRequestDto userCandidate);
    UserRequestDto userToUserDto(User user);
    UserResponseDto userToUserResponseDto(User user);
    default Role mapRole(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}
