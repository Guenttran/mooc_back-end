package org.example.backend.controller;

import lombok.AllArgsConstructor;
import org.example.backend.dto.job.JobRequestDTO;
import org.example.backend.dto.job.JobResponseDTO;
import org.example.backend.service.job.JobServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@CrossOrigin("*")
@AllArgsConstructor
public class JobController {
    private final JobServiceImpl jobService;

    @GetMapping
    public Page<JobRequestDTO> getJobs(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                                       @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                       @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobService.getJobs(pageable, search);
    }

    @PostMapping
    public ResponseEntity<?> addJob(@Validated @RequestBody JobResponseDTO job, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        jobService.createJob(job);
        return ResponseEntity.ok().body("Job created successfully!");
    }

    @GetMapping("/{id}")
    public JobResponseDTO getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(
            @PathVariable Long id,
            @Validated @RequestBody JobResponseDTO job,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        jobService.updateJob(id, job);
        return ResponseEntity.ok().body("Job updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().body("Job deleted successfully!");
    }
}
