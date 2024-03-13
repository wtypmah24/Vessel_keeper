package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.vessel.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselRepository extends JpaRepository<Vessel, Long> {
    Vessel findById(long imoNumber);
}
