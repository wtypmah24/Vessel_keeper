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
class VoyageRepositoryTest {

    @Autowired
    private VoyageRepository repository;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private VesselRepository vesselRepository;
    private Voyage voyage;

    @BeforeEach
    void setUp() {
        voyage = new Voyage();
        voyage.setStartDate(LocalDate.of(2024, 2, 1));
        voyage.setEndDate(LocalDate.of(2024, 6, 1));
        voyage.setPortOfLoading("Hamburg");
        voyage.setPortOfDischarging("Balboa");
    }

    @Test
    void addVoyage() {
        Voyage savedVoyage = repository.save(voyage);
        Voyage reviewedVoyage = entityManager.find(Voyage.class, savedVoyage.getId());
        assertEquals(savedVoyage.getId(), reviewedVoyage.getId());
    }

    @Test
    void findById() {
        Voyage savedVoyage = repository.save(voyage);
        Voyage reviewedVoyage = entityManager.find(Voyage.class, savedVoyage.getId());
        Voyage targetVoyage = repository.findById(savedVoyage.getId()).orElseThrow();
        assertEquals(reviewedVoyage, targetVoyage);
    }

    @Test
    void findByVesselIsNull() {
        Vessel vessel = new Vessel();
        Voyage signedVoyage = new Voyage();
        vesselRepository.save(vessel);
        signedVoyage.setVessel(vessel);
        repository.save(signedVoyage);
        repository.save(voyage);
        List<Voyage> voyageList = repository.findByVesselIsNull();
        assertEquals(1, voyageList.size());
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindByPortOfDischargingAndEndDateAndVesselIsNotNull")
    void findByPortOfDischargingAndEndDateAndVesselIsNotNull(String portOfDischarging, LocalDate endDate, boolean isFind) {
        Vessel vessel = new Vessel();
        vesselRepository.save(vessel);
        voyage.setVessel(vessel);
        repository.save(voyage);
        List<Voyage> voyages = repository.findByPortOfDischargingAndEndDateAndVesselIsNotNull(portOfDischarging, endDate);
        assertEquals(voyages.size() == 1, isFind);
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindByPortOfDischargingAndEndDateAndVesselIsNull")
    void findByPortOfDischargingAndEndDateAndVesselIsNull(String portOfDischarging, LocalDate endDate, boolean isFind) {
        repository.save(voyage);
        List<Voyage> voyages = repository.findByPortOfDischargingAndEndDateAndVesselIsNotNull(portOfDischarging, endDate);
        assertEquals(voyages.size() == 1, isFind);
    }

    static Stream<Arguments> provideDataForFindByPortOfDischargingAndEndDateAndVesselIsNotNull() {
        return Stream.of(
                Arguments.of("Balboa", LocalDate.of(2024, 6, 1), true),
                Arguments.of("Balboa", LocalDate.of(2024, 4, 1), false),
                Arguments.of("Antwerp", LocalDate.of(2024, 6, 1), false)
        );
    }

    static Stream<Arguments> provideDataForFindByPortOfDischargingAndEndDateAndVesselIsNull() {
        return Stream.of(
                Arguments.of("Balboa", LocalDate.of(2024, 6, 1), false),
                Arguments.of("Balboa", LocalDate.of(2024, 4, 1), false),
                Arguments.of("Antwerp", LocalDate.of(2024, 6, 1), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindByPortOfLoadingAndStartDateAndVesselIsNull")
    void findByPortOfLoadingAndStartDateAndVesselIsNull(String portOfLoading, LocalDate startDate, boolean isFind) {
        repository.save(voyage);
        List<Voyage> voyages = repository.findByPortOfLoadingAndStartDateAndVesselIsNull(portOfLoading, startDate);
        assertEquals(voyages.size() == 1, isFind);
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindByPortOfLoadingAndStartDateAndVesselIsNotNull")
    void findByPortOfLoadingAndStartDateAndVesselIsNotNull(String portOfLoading, LocalDate startDate, boolean isFind) {
        Vessel vessel = new Vessel();
        vesselRepository.save(vessel);
        voyage.setVessel(vessel);
        repository.save(voyage);
        List<Voyage> voyages = repository.findByPortOfLoadingAndStartDateAndVesselIsNull(portOfLoading, startDate);
        assertEquals(voyages.size() == 1, isFind);
    }

    static Stream<Arguments> provideDataForFindByPortOfLoadingAndStartDateAndVesselIsNull() {
        return Stream.of(
                Arguments.of("Hamburg", LocalDate.of(2024, 2, 1), true),
                Arguments.of("Hamburg", LocalDate.of(2024, 4, 1), false),
                Arguments.of("Antwerp", LocalDate.of(2024, 6, 1), false)
        );
    }

    static Stream<Arguments> provideDataForFindByPortOfLoadingAndStartDateAndVesselIsNotNull() {
        return Stream.of(
                Arguments.of("Hamburg", LocalDate.of(2024, 2, 1), false),
                Arguments.of("Hamburg", LocalDate.of(2024, 4, 1), false),
                Arguments.of("Antwerp", LocalDate.of(2024, 6, 1), false)
        );
    }


}