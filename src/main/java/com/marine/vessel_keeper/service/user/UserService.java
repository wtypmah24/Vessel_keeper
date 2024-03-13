package com.marine.vessel_keeper.service.user;

import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.dto.response.UserResponseDto;
import com.marine.vessel_keeper.mapper.UserMapper;
import com.marine.vessel_keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public UserResponseDto createUser(UserRequestDto userCandidate) {
        return userMapper.userToUserResponseDto(userRepository.save(userMapper.userDtoToUser(userCandidate)));
    }
    @Transactional
    public void deleteUser(String login){
        userRepository.deleteById(userRepository.findUserByLogin(login).orElseThrow().getId());
    }
}