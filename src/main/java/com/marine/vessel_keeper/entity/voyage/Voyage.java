package com.marine.vessel_keeper.entity.voyage;

import com.marine.vessel_keeper.entity.vessel.Vessel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "voyage")
@Data
@NoArgsConstructor
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "port_of_loading")
    private String portOfLoading;
    @Column(name = "port_of_discharging")
    private String portOfDischarging;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @OneToOne
    private Vessel vessel;


    public long getId() {
        return id;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public String getPortOfDischarging() {
        return portOfDischarging;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }
}