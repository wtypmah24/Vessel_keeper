package com.marine.vessel_keeper.dto.request;

public record UserRequestDto(String fullName, String login, String password, String role) {
}