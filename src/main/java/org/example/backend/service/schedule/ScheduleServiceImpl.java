package org.example.backend.service.schedule;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.example.backend.entity.InterviewSchedule;
import org.example.backend.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public Page<InterviewScheduleDTO> getAllSchedules(Pageable pageable, String search) {
        Page<InterviewSchedule> page = scheduleRepository.findBy("%" + search + "%",pageable);
        return page.map(x -> {
            InterviewScheduleDTO dto = new InterviewScheduleDTO();
            BeanUtils.copyProperties(x,dto);
            dto.setInterviewer(x.getRegister().getJob().getEmployee().getLastName());
            dto.setRecruiter(x.getRegister().getCandidate().getRecruiter().getLastName());
            dto.setCandidateName(x.getRegister().getCandidate().getFullname());
            dto.setJob(x.getRegister().getJob().getJobName());
            return dto;
        });
    }

    @Override
    public InterviewScheduleDTO getScheduleById(Long id) {
        InterviewSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        InterviewScheduleDTO dto = new InterviewScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        dto.setInterviewer(schedule.getRegister().getJob().getEmployee().getLastName());
        dto.setRecruiter(schedule.getRegister().getCandidate().getRecruiter().getLastName());
        dto.setCandidateName(schedule.getRegister().getCandidate().getFullname());
        dto.setJob(schedule.getRegister().getJob().getJobName());
        return dto;
    }

    @Override
    public InterviewScheduleDTO createSchedule(InterviewScheduleDTO scheduleDTO) {
        InterviewSchedule schedule = new InterviewSchedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        InterviewSchedule savedSchedule = scheduleRepository.save(schedule);

        InterviewScheduleDTO savedScheduleDTO = new InterviewScheduleDTO();
        BeanUtils.copyProperties(savedSchedule, savedScheduleDTO);
        return savedScheduleDTO;
    }

    @Override
    public InterviewScheduleDTO updateSchedule(Long id, InterviewScheduleDTO scheduleDTO) {
        InterviewSchedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        BeanUtils.copyProperties(scheduleDTO, existingSchedule, "scheduleId");

        InterviewSchedule updatedSchedule = scheduleRepository.save(existingSchedule);

        InterviewScheduleDTO updatedScheduleDTO = new InterviewScheduleDTO();
        BeanUtils.copyProperties(updatedSchedule, updatedScheduleDTO);
        return updatedScheduleDTO;
    }

    @Override
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }

}
