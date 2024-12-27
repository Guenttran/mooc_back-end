package org.example.backend.controller;

import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.example.backend.service.schedule.ScheduleServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin("*")
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;

    public ScheduleController(ScheduleServiceImpl scheduleService) {
        this.scheduleService = scheduleService;
    }

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

    @PostMapping
    public ResponseEntity<InterviewScheduleDTO> createSchedule(@RequestBody InterviewScheduleDTO scheduleDTO) {
        InterviewScheduleDTO createdSchedule = scheduleService.createSchedule(scheduleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewScheduleDTO> updateSchedule(
            @PathVariable Long id,
            @RequestBody InterviewScheduleDTO scheduleDTO) {
        InterviewScheduleDTO updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
