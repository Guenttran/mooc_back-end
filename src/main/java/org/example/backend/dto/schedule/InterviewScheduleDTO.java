package org.example.backend.dto.schedule;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewScheduleDTO {
    private Long scheduleId;
    private String scheduleTitle;
    private String candidateName;
    private String interviewer;
    private String recruiter;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private String location;
    private String meetingLink;
    private String result;
    private String status;
    private String job;
}
