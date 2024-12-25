package org.example.backend.service.employee;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.employee.EmployeeCreateDTO;
import org.example.backend.entity.Department;
import org.example.backend.entity.Employee;
import org.example.backend.repository.DepartmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final DepartmentRepository departmentRepository;

    public void save(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeCreateDTO, employee);

    }
}
