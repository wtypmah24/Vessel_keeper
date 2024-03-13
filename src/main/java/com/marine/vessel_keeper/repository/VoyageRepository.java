package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
}
