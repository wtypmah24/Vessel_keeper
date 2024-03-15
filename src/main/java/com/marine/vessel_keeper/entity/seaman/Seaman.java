package com.marine.vessel_keeper.entity.seaman;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.Certificate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seaman")
@Data
@NoArgsConstructor
public class Seaman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "rank")
    private Rank rank;
    @Column(name = "has_job")
    private boolean hasJob = false;
    @OneToMany
    private Set<SeamanCertificate> certificates = new HashSet<>();
    @OneToMany
    private Set<RecordOfService> recordOfServices = new HashSet<>();

    public Set<SeamanCertificate> addCertificate(SeamanCertificate certificate) {
        this.certificates.add(certificate);
        certificate.setSeaman(this);
        return certificates;
    }

    public Set<SeamanCertificate> removeCertificate(SeamanCertificate certificate){
        this.certificates.remove(certificate);
        return certificates;
    }
}