package org.example.backend.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.entity.Skill;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequestDTO {
    private Long jobId;

    private String jobName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String level;

    private List<Skill> skills;

    private String status;
}
