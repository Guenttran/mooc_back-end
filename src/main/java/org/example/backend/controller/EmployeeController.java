package org.example.backend.controller;

import org.example.backend.dto.employee.EmployeeCreateDTO;
import org.example.backend.entity.Employee;
import org.example.backend.service.employee.EmployeeService;
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

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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


}
