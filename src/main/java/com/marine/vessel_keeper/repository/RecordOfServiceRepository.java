package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.seaman.RecordOfService;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordOfServiceRepository extends JpaRepository<RecordOfService, Long> {
}
