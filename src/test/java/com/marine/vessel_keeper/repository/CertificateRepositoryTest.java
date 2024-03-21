package com.marine.vessel_keeper.repository;

import com.marine.vessel_keeper.entity.seaman.SeamanCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CertificateRepositoryTest {
    @Autowired
    CertificateRepository repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SeamanCertificate certificate;

    @BeforeEach
    void setUp(){
        certificate = new SeamanCertificate();
        certificate.setName("SOLAS");
    }

    @Test
    void findById() {
        SeamanCertificate savedCertificate = repository.save(certificate);
        Optional<SeamanCertificate> result = repository.findById(savedCertificate.getId());
        assertEquals(savedCertificate.getName(), result.get().getName());
    }

    @Test
    void save() {
        SeamanCertificate savedCertificate = repository.save(certificate);
        assertEquals(savedCertificate.getName(), certificate.getName());
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM seaman_certificate WHERE id = ?", savedCertificate.getId());
        List<SeamanCertificate> certificates = repository.findAll();
        assertEquals(1, certificates.size());
        assertEquals(1, result.size());
    }

    @Test
    void remove() {
        repository.save(certificate);
        assertFalse(repository.findAll().isEmpty());
        repository.delete(certificate);
        assertTrue(repository.findAll().isEmpty());
    }
}