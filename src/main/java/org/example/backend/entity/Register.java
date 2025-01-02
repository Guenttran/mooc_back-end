package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.utils.enums.CandidateStatus;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_id, candidate_id"})
})
public class Register extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "register_id")
    private Long registerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToOne(fetch = FetchType.LAZY)
    private InterviewSchedule interviewSchedule;

    @OneToOne(mappedBy = "register", orphanRemoval = true)
    private Offer offer;
}
