package org.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Negative;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate_skill", uniqueConstraints = {
        @UniqueConstraint(name = "un_skill_candidate", columnNames = {"skill_id","candidate_id" })
})
public class CandidateSkill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
