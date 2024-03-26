package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.user.Role;
import com.marine.vessel_keeper.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);
    List<User> findUserByRole(Role role);
}