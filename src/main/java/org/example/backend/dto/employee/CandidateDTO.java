package org.example.backend.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.utils.enums.CandidateStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private Long candidateId;

    private String fullname;

    private String email;

    private String phone;

    private String address;

    private Boolean gender;

    private CandidateStatus status;

    private String cv;

    private String description;

}
