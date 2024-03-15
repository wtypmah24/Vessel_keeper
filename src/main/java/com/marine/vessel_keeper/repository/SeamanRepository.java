package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.seaman.Seaman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeamanRepository extends JpaRepository<Seaman, Long> {
    Optional<Seaman> findById(long id);
}
