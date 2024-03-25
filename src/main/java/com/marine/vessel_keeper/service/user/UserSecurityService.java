package com.marine.vessel_keeper.service.user;

import com.marine.vessel_keeper.entity.user.User;
import com.marine.vessel_keeper.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository repository;

    public UserSecurityService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.findUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
