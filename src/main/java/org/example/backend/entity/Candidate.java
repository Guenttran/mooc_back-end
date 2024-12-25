package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.utils.enums.CandidateStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "candidates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long candidateId;

    private String fullname;

    private String email;

    private String phone;

    private String address;

    private Boolean gender;

    @Enumerated(EnumType.STRING)
    private CandidateStatus status;

    private String cv;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "year_of_experience")
    private Integer yearOfExperience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    private Employee recruiter;

    @OneToMany(mappedBy = "candidate")
    private List<CandidatePosition> candidatePositions;

    @OneToMany(mappedBy = "candidate")
    private List<Register> registers;
}
