package com.marine.vessel_keeper.entity.vessel;

import com.marine.vessel_keeper.entity.seaman.Seaman;
import com.marine.vessel_keeper.entity.voyage.Voyage;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "vessel")
@Data
@EqualsAndHashCode(exclude = {"crew"})
@ToString(exclude = {"crew"})
@NoArgsConstructor
public class Vessel {
    @Id
    private long imoNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "vessel_type")
    private VesselType vesselType;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seaman> crew = new HashSet<>();
    @OneToOne
    private Voyage voyage;

    public Set<Seaman> addSeamanToCrew(Seaman seaman){
        this.crew.add(seaman);
        seaman.setHasJob(true);
        return crew;
    }
    public void signOffSeaman(Seaman seaman){
        crew.remove(seaman);
        seaman.setHasJob(false);
    }

}
