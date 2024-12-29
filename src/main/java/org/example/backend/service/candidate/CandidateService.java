package org.example.backend.service.candidate;

import org.example.backend.dto.employee.CandidateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CandidateService {

    void save(CandidateDTO candidateDTO, MultipartFile cv);

    void update(Long id, CandidateDTO candidateDTO, MultipartFile cv);

    void saveWithCv(CandidateDTO candidateDTO, MultipartFile cv) throws IOException;
}
