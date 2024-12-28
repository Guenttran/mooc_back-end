package org.example.backend.service.job;

import lombok.AllArgsConstructor;
import org.example.backend.dto.job.JobInterviewDTO;
import org.example.backend.entity.Job;
import org.example.backend.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;

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

    
}
