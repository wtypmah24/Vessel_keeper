package com.marine.vessel_keeper.service.user;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.entity.user.Role;
import com.marine.vessel_keeper.exception.UserException;
import com.marine.vessel_keeper.mapper.UserMapper;
import com.marine.vessel_keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto userCandidate) throws UserException {
        checkUserCandidate(userCandidate);
        return userMapper.userToUserResponseDto(userRepository.save(userMapper.userDtoToUser(userCandidate)));
    }

    @Transactional
    public void deleteUser(String login) throws UserException {
        userRepository
                .deleteById(userRepository
                        .findUserByLogin(login)
                        .orElseThrow(() -> new UserException("Can't find user with login: " + login))
                        .getId());
    }

    private void checkUserCandidate(UserRequestDto candidate) throws UserException {
        if (candidate == null) throw new UserException("You provided empty candidate!");
        if (candidate.login() == null || candidate.login().isBlank()) throw new UserException("You didn't provide user name");
        if (candidate.password() == null || candidate.password().isBlank()) throw new UserException("You didn't provide password!");
        if (candidate.role() == null || candidate.role().isBlank()) throw new UserException("You didn't provide user's role!");
        try {
            Role.valueOf(candidate.role().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UserException("Invalid role specified: " + candidate.role());
        }
    }
}