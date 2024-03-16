package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.VesselType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface SeamanRepository extends JpaRepository<Seaman, Long> {
    Optional<Seaman> findById(long id);

    @Query("SELECT s FROM Seaman s WHERE s.hasJob = false AND EXISTS (SELECT 1 FROM RecordOfService r WHERE r.seaman = s AND r.vessel.vesselType = :vesselType)")
    Set<Seaman> findSeamenByShipType(VesselType vesselType);
}
