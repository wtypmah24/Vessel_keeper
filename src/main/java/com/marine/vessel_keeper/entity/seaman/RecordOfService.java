package com.marine.vessel_keeper.entity.seaman;

import com.marine.vessel_keeper.entity.vessel.Vessel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record_of_service")
@Data
@NoArgsConstructor
public class RecordOfService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Seaman seaman;
    @ManyToOne
    private Vessel vessel;
    @Column(name = "comment")
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Seaman getSeaman() {
        return seaman;
    }

    public void setSeaman(Seaman seaman) {
        this.seaman = seaman;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}