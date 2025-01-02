package org.example.backend.service.job;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.backend.dto.job.JobInterviewDTO;
import org.example.backend.dto.job.JobRequestDTO;
import org.example.backend.dto.job.JobResponseDTO;
import org.example.backend.entity.Job;
import org.example.backend.entity.JobSkill;
import org.example.backend.entity.Skill;
import org.example.backend.repository.JobRepository;
import org.example.backend.repository.JobSkillRepository;
import org.example.backend.repository.SkillRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.backend.utils.enums.JobStatus.OPEN;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;
    private SkillRepository skillRepository;
    private JobSkillRepository jobSkillRepository;

    public List<JobInterviewDTO> getAllOpenJobs() {
        List<Job> jobs = jobRepository.findAllOpenJobs();
        return jobs.stream()
                .map(job -> {
                    JobInterviewDTO dto = new JobInterviewDTO();
                    dto.setJobId(job.getJobId());
                    dto.setJobName(job.getJobName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Page<JobRequestDTO> getJobs(Pageable pageable, String search) {
        Page<Job> jobs = jobRepository.getBy(pageable, "%" + search + "%");
        return jobs.map(this::convertToDTO);
    }

    public JobRequestDTO convertToDTO(Job job) {
        JobRequestDTO dto = new JobRequestDTO();
        BeanUtils.copyProperties(job, dto);
        dto.setLevel(job.getLevel().name());
        dto.setSkills(jobRepository.getSkillsByJobId(job.getJobId()));
        return dto;
    }

    public JobResponseDTO getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
        JobResponseDTO dto = new JobResponseDTO();
        BeanUtils.copyProperties(job, dto);
        dto.setLevel(job.getLevel().name());
        dto.setSkills(jobRepository.getSkillsByJobId(job.getJobId()));
        return dto;
    }

    public void updateJob(Long id, JobResponseDTO jobDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
        BeanUtils.copyProperties(jobDTO, job, "skills");
        jobRepository.save(job);
        jobSkillRepository.deleteByJob(job);
        jobSkillAdd(jobDTO, job);
    }

    public void createJob(JobResponseDTO jobDTO) {
        Job job = new Job();
        BeanUtils.copyProperties(jobDTO, job, "skills");
        job.setStatus(OPEN);
        job = jobRepository.save(job);
        jobSkillAdd(jobDTO, job);
    }

    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found"));
        jobSkillRepository.deleteByJob(job);
        jobRepository.delete(job);
    }

    public void jobSkillAdd(JobResponseDTO jobDTO , Job job) {
        if (jobDTO.getSkills() != null && !jobDTO.getSkills().isEmpty()) {
            List<JobSkill> jobSkills = jobDTO.getSkills().stream()
                    .map(skill -> {
                        if (skill.getSkillId() == null) {
                            throw new IllegalArgumentException("Skill ID cannot be null");
                        }
                        Skill existingSkill = skillRepository.findById(skill.getSkillId())
                                .orElseThrow(() -> new EntityNotFoundException("Skill not found: " + skill.getSkillId()));
                        return JobSkill.builder()
                                .job(job)
                                .skill(existingSkill)
                                .build();
                    })
                    .toList();
            jobSkillRepository.saveAll(jobSkills);
        }
    }
}
