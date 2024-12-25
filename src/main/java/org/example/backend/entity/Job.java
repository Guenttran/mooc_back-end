package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.utils.enums.JobStatus;
import org.example.backend.utils.enums.Level;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "salary_from")
    private BigDecimal salaryFrom;

    @Column(name = "salary_to")
    private BigDecimal salaryTo;

    @Column(name = "working_at")
    private String workingAt;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @OneToMany(mappedBy = "job")
    private List<Register> registers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
