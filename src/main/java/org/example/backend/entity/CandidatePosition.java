package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "candidate_position", uniqueConstraints ={
        @UniqueConstraint(name = "un_position_candidate", columnNames = {"position_id","candidate_id"})
})
public class CandidatePosition extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
