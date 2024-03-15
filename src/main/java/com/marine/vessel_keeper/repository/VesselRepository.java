package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.vessel.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VesselRepository extends JpaRepository<Vessel, Long> {
    Optional<Vessel> findByImoNumber(long imoNumber);

    List<Vessel> findVesselsByVoyage_PortOfDischargingAndVoyage_EndDate(String dischargingPort, LocalDate endDate);
}
