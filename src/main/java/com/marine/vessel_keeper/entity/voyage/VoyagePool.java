package com.marine.vessel_keeper.entity.voyage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Entity
@Table(name = "voyage_pool")
@Data
@NoArgsConstructor
public class VoyagePool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private Set<Voyage> availableVoyages;
}
