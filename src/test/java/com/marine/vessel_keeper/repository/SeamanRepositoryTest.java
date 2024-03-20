package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.seaman.RecordOfService;
import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import com.marine.vessel_keeper.entity.vessel.VesselType;
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
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SeamanRepositoryTest {

    @Autowired
    private SeamanRepository seamanRepository;
    @Autowired
    private RecordOfServiceRepository recordOfServiceRepository;
    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Seaman seaman;
    @BeforeEach
    void setUp(){
        seaman = new Seaman();
    }

    @Test
    void findById() {
        Seaman savedSeaman = seamanRepository.save(seaman);
        assertEquals(savedSeaman, entityManager.find(Seaman.class, savedSeaman.getId()));
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindSeamenByShipType")
    void findSeamenByShipType(VesselType vesselType, boolean isFind) {
        Vessel vessel = new Vessel();
        vessel.setVesselType(VesselType.GENERAL_CARGO);
        vessel.setImoNumber(1);
        vesselRepository.save(vessel);

        RecordOfService record = new RecordOfService();
        record.setVessel(vessel);
        recordOfServiceRepository.save(record);

        Seaman seaman = new Seaman();
        seamanRepository.save(seaman);
        seaman.addServiceRecord(record);
        seamanRepository.save(seaman);
        Set<Seaman> seamanList = seamanRepository.findSeamenByShipType(vesselType);
        assertEquals(isFind, !seamanList.isEmpty());
    }
    static Stream<Arguments> provideDataForFindSeamenByShipType() {
        return Stream.of(
                Arguments.of(VesselType.HEAVY_LIFT, false),
                Arguments.of(VesselType.GENERAL_CARGO, true),
                Arguments.of(VesselType.BULKER, false)
        );
    }
}