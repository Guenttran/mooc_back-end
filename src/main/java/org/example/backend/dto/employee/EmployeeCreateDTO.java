package org.example.backend.dto.employee;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateDTO {

    @NotBlank(message = "{firstname.required}")
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String role;
    private String department;
    private String city;
    private String country;
}
