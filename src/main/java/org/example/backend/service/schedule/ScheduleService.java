package org.example.backend.service.schedule;

import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    Page<InterviewScheduleDTO> getAllSchedules(Pageable pageable, String search);

    InterviewScheduleDTO getScheduleById(Long id);
    void createSchedule(InterviewScheduleDTO scheduleDTO);
    void updateSchedule(Long id, InterviewScheduleDTO scheduleDTO);
    void deleteSchedule(Long id);
}
