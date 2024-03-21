package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class VesselRepositoryTest {
    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private VoyageRepository voyageRepository;
    @Autowired
    private TestEntityManager entityManager;
    private Vessel vessel;

    @BeforeEach
    void setUp() {
        vessel = new Vessel();
    }

    @Test
    void findByImoNumber() {
        Vessel savedVessel = vesselRepository.save(vessel);
        Vessel reviewedVessel = entityManager.find(Vessel.class, savedVessel.getImoNumber());
        assertEquals(savedVessel, reviewedVessel);
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindVesselsByVoyage_PortOfDischargingAndVoyage_EndDate")
    void findVesselsByVoyage_PortOfDischargingAndVoyage_EndDate(String dischargingPort, LocalDate endDate, boolean isFind) {
        Voyage voyage = new Voyage();
        voyage.setVessel(vessel);
        voyage.setPortOfDischarging("Balboa");
        voyage.setEndDate(LocalDate.of(2024, 6, 1));
        voyageRepository.save(voyage);
        vessel.setVoyage(voyage);
        vesselRepository.save(vessel);
        List<Vessel> vesselList = vesselRepository.findVesselsByVoyage_PortOfDischargingAndVoyage_EndDate(dischargingPort, endDate);

        assertEquals(vesselList.size() == 1, isFind);
    }

    static Stream<Arguments> provideDataForFindVesselsByVoyage_PortOfDischargingAndVoyage_EndDate() {
        return Stream.of(
                Arguments.of("Balboa", LocalDate.of(2024, 6, 1), true),
                Arguments.of("Balboa", LocalDate.of(2024, 4, 1), false),
                Arguments.of("Antwerp", LocalDate.of(2024, 6, 1), false)
        );
    }
}