package com.marine.vessel_keeper.entity.seaman;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "seaman_certificate")
@Data
@NoArgsConstructor
public class SeamanCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "expire_date")
    private LocalDate expireDate;
    @ManyToOne
    private Seaman seaman;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public Seaman getSeaman() {
        return seaman;
    }

    public void setSeaman(Seaman seaman) {
        this.seaman = seaman;
    }
}
