package com.marine.vessel_keeper.service.user;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.entity.user.Role;
import com.marine.vessel_keeper.exception.UserException;
import com.marine.vessel_keeper.mapper.UserMapper;
import com.marine.vessel_keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto userCandidate) throws UserException {
        checkUserCandidate(userCandidate);
        String hashPassword = passwordEncoder.encode(userCandidate.password());

        UserRequestDto newCandidate = new UserRequestDto(userCandidate.fullName(),
                userCandidate.login(),
                hashPassword,
                userCandidate.role());

        return userMapper.userToUserResponseDto(userRepository.save(userMapper.userDtoToUser(newCandidate)));
    }

    @Transactional
    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::userToUserResponseDto).toList();
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