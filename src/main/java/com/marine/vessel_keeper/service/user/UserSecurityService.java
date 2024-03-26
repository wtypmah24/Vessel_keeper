package com.marine.vessel_keeper.service.user;

import com.marine.vessel_keeper.entity.user.User;
import com.marine.vessel_keeper.entity.user.UserWithCredentials;
import com.marine.vessel_keeper.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository repository;

    public UserSecurityService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new UserWithCredentials(repository.findUserByLogin(login).orElseThrow());
    }
}