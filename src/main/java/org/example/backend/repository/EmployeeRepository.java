package org.example.backend.repository;

import org.example.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> getByUsername(String username);

    @Query("SELECT e FROM Employee e WHERE CONCAT(e.firstName, ' ', e.lastName) = :fullName")
    Optional<Employee> getByFullName(String fullName);
}
