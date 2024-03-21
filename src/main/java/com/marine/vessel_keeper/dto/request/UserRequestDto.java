package com.marine.vessel_keeper.dto.request;

import lombok.Builder;
import lombok.NoArgsConstructor;

public record UserRequestDto(String fullName, String login, String password, String role) {
}