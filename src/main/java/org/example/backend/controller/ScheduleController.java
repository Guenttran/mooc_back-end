package org.example.backend.controller;

import lombok.AllArgsConstructor;
import org.example.backend.dto.job.JobInterviewDTO;
import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.example.backend.service.job.JobServiceImpl;
import org.example.backend.service.schedule.ScheduleServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin("*")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;
    private final JobServiceImpl jobService;

    @GetMapping
    public Page<InterviewScheduleDTO> getAllSchedules(
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return scheduleService.getAllSchedules(pageable, search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewScheduleDTO> getScheduleById(@PathVariable Long id) {
        InterviewScheduleDTO schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/schedules/form-data")
    public ResponseEntity<Map<String, Object>> getAddFormData() {
        List<JobInterviewDTO> jobs = jobService.getAllOpenJobs();
        Map<String, Object> response = new HashMap<>();
        response.put("jobs", jobs);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addSchedule(@Validated @RequestBody InterviewScheduleDTO scheduleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        scheduleService.createSchedule(scheduleDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/schedules/{id}/form-data")
    public ResponseEntity<Map<String, Object>> getUpdateFormData(@PathVariable Long id) {
        InterviewScheduleDTO scheduleDTO = scheduleService.getScheduleById(id);
        List<JobInterviewDTO> jobs = jobService.getAllOpenJobs();
        Map<String, Object> response = new HashMap<>();
        response.put("schedule", scheduleDTO);
        response.put("jobs", jobs);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(
            @PathVariable Long id,
            @Validated @RequestBody InterviewScheduleDTO scheduleDTO,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        scheduleService.getScheduleById(id);
        scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
