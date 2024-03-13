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
}