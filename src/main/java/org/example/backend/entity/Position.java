package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "position_name")
    private String positionName;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @OneToMany(mappedBy = "position")
    private List<CandidatePosition> candidatePositions;
}
