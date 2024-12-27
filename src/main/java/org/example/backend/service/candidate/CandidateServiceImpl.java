package org.example.backend.service.candidate;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.employee.CandidateDTO;
import org.example.backend.repository.CandidateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;

//    @Override
//    public List<CandidateDTO> getAllCandidates() {
//        return candidateRepository.findAll().stream().map(c -> {
//            CandidateDTO dto = new CandidateDTO();
//            BeanUtils.copyProperties(c, dto);
//            return dto;
//        }).collect(Collectors.toList());
//    }

}
