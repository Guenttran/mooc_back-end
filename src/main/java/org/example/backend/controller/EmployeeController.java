package org.example.backend.controller;

import org.example.backend.dto.employee.EmployeeCreateDTO;
import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.example.backend.entity.Employee;
import org.example.backend.service.employee.EmployeeService;
import org.example.backend.service.schedule.ScheduleServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ScheduleServiceImpl scheduleService;

    public EmployeeController(EmployeeService employeeService, ScheduleServiceImpl scheduleService) {
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@Validated @ModelAttribute("employee") EmployeeCreateDTO employee,
                                      BindingResult bindingResult,
                                      @RequestParam("avatar") MultipartFile avatar) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid data!");
        }

        return ResponseEntity.ok().body(employee);

    }

    @GetMapping("/employee/{employeeId}/interviews")
    public Page<InterviewScheduleDTO> getInterviewsForEmployee(@PathVariable Long employeeId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleService.getSchedulesByEmployee(employeeId, pageable);
    }

}
