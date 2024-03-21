package com.marine.vessel_keeper.service.user;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.entity.user.User;
import com.marine.vessel_keeper.exception.UserException;
import com.marine.vessel_keeper.mapper.UserMapper;
import com.marine.vessel_keeper.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//TODO: Class needs to be refactored! Duplicated code!
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserService service;

    @Test
    void createUserMethod() throws UserException {
        UserRequestDto candidate = new UserRequestDto("test", "testLogin", "password", "OWNER");

        when(repository.save(any(User.class))).thenReturn(new User());
        when(mapper.userDtoToUser(any(UserRequestDto.class))).thenReturn(new User());
        when(mapper.userToUserResponseDto(any(User.class))).thenReturn(mock(UserResponseDto.class));

        UserResponseDto user = service.createUser(candidate);
        assertInstanceOf(UserResponseDto.class, user);
    }

    @Test
    void createUserCheck() throws UserException {
        UserRequestDto candidate = new UserRequestDto("test", "testLogin", "password", "OWNER");

        List<User> users = new ArrayList<>();
        when(mapper.userDtoToUser(any(UserRequestDto.class))).thenReturn(new User());
        when(mapper.userToUserResponseDto(any(User.class))).thenReturn(mock(UserResponseDto.class));
        doAnswer(invocation -> {
            User user = new User();
            users.add(user);
            return user;
        }).when(repository).save(any(User.class));
        service.createUser(candidate);
        assertFalse(users.isEmpty());
    }

    @Test
    void createUserNegativeCase() {
        assertThrows(UserException.class, () -> service.createUser(null));
    }

    @Test
    void deleteUser() throws UserException {
        String login = "testLogin";
        User user = new User();
        user.setId(1L);
        when(repository.findUserByLogin(login)).thenReturn(Optional.of(user));
        service.deleteUser(login);
        verify(repository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUserCheck() throws UserException {
        User user = new User();
        String login = "testLogin";
        user.setId(1L);
        user.setLogin(login);

        List<User> users = new ArrayList<>();
        users.add(user);

        when(repository.findUserByLogin(login)).thenReturn(Optional.of(user));
        doAnswer(u -> users.remove(users.get(0))).when(repository).deleteById(anyLong());

        service.deleteUser(login);
        assertTrue(users.isEmpty());
    }


    @Test
    void testDeleteUserUserNotFound() {
        String login = "nonExistingLogin";
        when(repository.findUserByLogin(login)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.deleteUser(login));
        verify(repository, never()).deleteById(anyLong());
    }
}