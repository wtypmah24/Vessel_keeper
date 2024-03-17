package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    Optional<Voyage> findById(long id);
    List<Voyage> findByVesselIsNull();
    List<Voyage> findByPortOfDischargingAndEndDateAndVesselIsNotNull(String portOfDischarging, LocalDate endDate);
    List<Voyage> findByPortOfLoadingAndStartDateAndVesselIsNull(String portOfLoading, LocalDate startDate);
}
