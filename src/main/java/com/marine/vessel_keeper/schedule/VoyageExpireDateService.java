package com.marine.vessel_keeper.schedule;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class VoyageExpireDateService {
    private final EntityManager entityManager;

    public VoyageExpireDateService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Scheduled(cron = "@daily")
    @Transactional
    public void removeExpiredVoyages() {
        LocalDate currentDate = LocalDate.now();
        Query query = entityManager.createQuery("DELETE FROM Voyage v WHERE v.endDate < :currentDate");
        query.setParameter("currentDate", currentDate);
        int deletedCount = query.executeUpdate();
        System.out.println("Удалено устаревших записей: " + deletedCount);
    }
}