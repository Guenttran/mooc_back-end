package org.example.backend.service.schedule;

import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    Page<InterviewScheduleDTO> getAllSchedules(Pageable pageable, String search);

    InterviewScheduleDTO getScheduleById(Long id);
    InterviewScheduleDTO createSchedule(InterviewScheduleDTO scheduleDTO);
    InterviewScheduleDTO updateSchedule(Long id, InterviewScheduleDTO scheduleDTO);
    void deleteSchedule(Long id);
}
