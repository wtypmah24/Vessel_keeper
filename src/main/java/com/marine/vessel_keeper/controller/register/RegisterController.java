package com.marine.vessel_keeper.controller.register;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.exception.UserException;
import com.marine.vessel_keeper.service.user.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Hidden
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registration(@RequestBody UserRequestDto userRequestDto) throws UserException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registration(userRequestDto));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(UserException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}