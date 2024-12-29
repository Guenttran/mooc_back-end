package org.example.backend.service.candidate;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.employee.CandidateDTO;
import org.example.backend.entity.Candidate;
import org.example.backend.repository.CandidateRepository;
import org.example.backend.utils.pdf.PdfUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;

    public void save(CandidateDTO candidateDTO, MultipartFile cv) {
        Candidate candidate = new Candidate();
        BeanUtils.copyProperties(candidateDTO, candidate);
        candidateRepository.save(candidate);
    }

    
    public void update(Long id, CandidateDTO candidateDTO, MultipartFile cv) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(candidateDTO, candidate);
        candidateRepository.save(candidate);
    }

    @Override
    public void saveWithCv(CandidateDTO candidateDTO, MultipartFile cv) throws IOException {
        if (cv.isEmpty()) {
            throw new IllegalArgumentException("CV file is required.");
        }
        if (!cv.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Invalid CV file type! Only PDF files are allowed.");
        }

        String cvContent = PdfUtils.extractTextFromPdf(cv);
        Candidate candidate = new Candidate();
        BeanUtils.copyProperties(candidateDTO, candidate);
        candidate.setCv(cvContent);
        candidateRepository.save(candidate);


    }
}
