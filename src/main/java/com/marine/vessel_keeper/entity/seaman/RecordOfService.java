package com.marine.vessel_keeper.entity.seaman;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.marine.vessel_keeper.entity.vessel.Vessel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record_of_service")
@Data
@EqualsAndHashCode(exclude = {"seaman"})
@NoArgsConstructor
public class RecordOfService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seaman_id")
    private Seaman seaman;
    @ManyToOne(fetch = FetchType.LAZY)
    private Vessel vessel;
    @Column(name = "comment")
    private String comment;
}