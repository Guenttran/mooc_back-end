package org.example.backend.service.schedule;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.example.backend.entity.Candidate;
import org.example.backend.entity.InterviewSchedule;
import org.example.backend.entity.Register;
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
    private final RegisterRepository registerRepository;
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Page<InterviewScheduleDTO> getAllSchedules(Pageable pageable, String search) {
        Page<InterviewSchedule> page = scheduleRepository.findBy("%" + search + "%",pageable);
        return page.map(this::convertToDTO);
    }

    @Override
    public InterviewScheduleDTO getScheduleById(Long id) {
        InterviewSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        return convertToDTO(schedule);
    }

    @Override
    public void createSchedule(InterviewScheduleDTO scheduleDTO) {
        InterviewSchedule schedule = new InterviewSchedule();
        Register register = dataEdit(scheduleDTO);

        schedule.setRegister(register);
        BeanUtils.copyProperties(scheduleDTO, schedule);

        register.setInterviewSchedule(schedule);
        registerRepository.save(register);
        scheduleRepository.save(schedule);

    }

    @Override
    public void updateSchedule(Long id, InterviewScheduleDTO scheduleDTO) {
        InterviewSchedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        BeanUtils.copyProperties(scheduleDTO, existingSchedule, "scheduleId");
        Register register = registerRepository.getByInterviewSchedule(existingSchedule)
                .orElseThrow(() -> new EntityNotFoundException("Register not found"));
        register.setJob(jobRepository.getByJobName(scheduleDTO.getJob())
                .orElseThrow(() -> new EntityNotFoundException("Job not found")));
        register.setCandidate(candidateRepository.getByFullname(scheduleDTO.getCandidateName())
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found")));
        registerRepository.save(register);
        existingSchedule.setRegister(register);
        scheduleRepository.save(existingSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }

    public InterviewScheduleDTO convertToDTO(InterviewSchedule schedule) {
        InterviewScheduleDTO dto = new InterviewScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        dto.setInterviewer(schedule.getRegister().getJob().getEmployee().getLastName());
        dto.setRecruiter(schedule.getRegister().getCandidate().getRecruiter().getUsername());
        dto.setCandidateName(schedule.getRegister().getCandidate().getFullname());
        dto.setJob(schedule.getRegister().getJob().getJobName());
        return dto;
    }

    public Register dataEdit(InterviewScheduleDTO scheduleDTO) {
        Register register = new Register();
        Candidate candidate = candidateRepository.getByFullname(scheduleDTO.getCandidateName())
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
        register.setCandidate(candidate);
        register.setJob(jobRepository.getByJobName((scheduleDTO.getJob()))
                .orElseThrow(() -> new EntityNotFoundException("Job not found")));
        candidate.setRecruiter(employeeRepository.getByUsername((scheduleDTO.getRecruiter()))
                .orElseThrow(() -> new EntityNotFoundException("Recruiter not found")));
        candidateRepository.save(candidate);
        return register;
    }

}
