package org.example.backend.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.entity.Skill;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDTO {
    private Long jobId;

    private String jobName;

    private String startDate;

    private String endDate;

    private BigDecimal salaryFrom;

    private BigDecimal salaryTo;

    private String workingAt;

    private List<Skill> skills;

    private String level;

    private String status;

    private String description;
}
