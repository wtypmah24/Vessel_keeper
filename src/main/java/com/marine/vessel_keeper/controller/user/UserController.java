package com.marine.vessel_keeper.controller.user;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.exception.UserException;
import com.marine.vessel_keeper.service.user.UserService;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NotContextException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) throws UserException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }
    @GetMapping("/get")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{login}")
    public void deleteUser(@PathVariable String login) throws UserException {
        userService.deleteUser(login);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(UserException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}