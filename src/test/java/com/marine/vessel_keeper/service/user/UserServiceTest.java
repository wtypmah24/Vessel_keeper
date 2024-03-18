package com.marine.vessel_keeper.service.user;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.entity.user.User;
import com.marine.vessel_keeper.exception.WrongCandidateException;
import com.marine.vessel_keeper.mapper.UserMapper;
import com.marine.vessel_keeper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
//TODO: Class needs to be refactored! Duplicated code!
@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserService service;

    @Test
    void createUserMethod() throws WrongCandidateException {
        UserRequestDto candidate = mock(UserRequestDto.class);

        when(repository.save(any(User.class))).thenReturn(new User());
        when(mapper.userDtoToUser(any(UserRequestDto.class))).thenReturn(new User());
        when(mapper.userToUserResponseDto(any(User.class))).thenReturn(mock(UserResponseDto.class));

        UserResponseDto user = service.createUser(candidate);
        assertInstanceOf(UserResponseDto.class, user);
    }

    @Test
    void createUserCheck() throws WrongCandidateException {
        UserRequestDto candidate = mock(UserRequestDto.class);

        List<User> users = new ArrayList<>();
        when(mapper.userDtoToUser(any(UserRequestDto.class))).thenReturn(new User());
        when(mapper.userToUserResponseDto(any(User.class))).thenReturn(mock(UserResponseDto.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        doAnswer(invocation -> {
            User user = userCaptor.getValue();
            users.add(user);
            return null;
        }).when(repository).save(userCaptor.capture());


        service.createUser(candidate);
        assertFalse(users.isEmpty());
    }

    @Test
    void createUserNegativeCase() {
        assertThrows(WrongCandidateException.class, () -> service.createUser(null));
    }

    @Test
    void deleteUser() {
        String login = "testLogin";
        User user = new User();
        user.setId(1L);
        when(repository.findUserByLogin(login)).thenReturn(Optional.of(user));
        service.deleteUser(login);
        verify(repository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUserCheck() {
        User user = new User();
        String login = "testLogin";
        user.setId(1L);
        user.setLogin(login);

        List<User> users = new ArrayList<>();
        users.add(user);

        when(repository.findUserByLogin(login)).thenReturn(Optional.of(user));
        doAnswer(u-> users.remove(users.get(0))).when(repository).deleteById(anyLong());

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