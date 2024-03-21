package com.marine.vessel_keeper.entity.seaman;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seaman")
@Data
@EqualsAndHashCode(exclude = {"recordOfServices", "certificates", "hasJob"})
@ToString(exclude = {"recordOfServices", "certificates"})
@NoArgsConstructor
public class Seaman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "rank")
    @Enumerated(EnumType.STRING)
    private Rank rank;
    @Column(name = "has_job")
    private boolean hasJob = false;
    @OneToMany(mappedBy = "seaman", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SeamanCertificate> certificates = new HashSet<>();
    @OneToMany(mappedBy = "seaman", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecordOfService> recordOfServices = new HashSet<>();

    public Set<SeamanCertificate> addCertificate(SeamanCertificate certificate) {
        this.certificates.add(certificate);
        certificate.setSeaman(this);
        return certificates;
    }

    public Set<SeamanCertificate> removeCertificate(SeamanCertificate certificate) {
        this.certificates.remove(certificate);
        return certificates;
    }

    public void addServiceRecord(RecordOfService record) {
        this.recordOfServices.add(record);
        record.setSeaman(this);
    }
}