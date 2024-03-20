package com.marine.vessel_keeper.entity.seaman;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "seaman_certificate")
@Data
@EqualsAndHashCode(exclude = {"seaman"})
@NoArgsConstructor
public class SeamanCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "expire_date")
    private LocalDate expireDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seaman_id")
    private Seaman seaman;
}
