package org.example.backend.controller;

import org.example.backend.dto.employee.CandidateDTO;
import org.example.backend.entity.Candidate;
import org.example.backend.repository.CandidateRepository;
import org.example.backend.service.candidate.CandidateService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/candidate")
@CrossOrigin("*")
public class CandidateController {

    private final CandidateService candidateService;
    private final CandidateRepository candidateRepository;

    public CandidateController(CandidateService candidateService, CandidateRepository candidateRepository) {
        this.candidateService = candidateService;
        this.candidateRepository = candidateRepository;
    }



//    @GetMapping
//    public ResponseEntity<?> getAllCandidates() {
//        List<CandidateDTO> candidates = candidateService.getAllCandidates();
//        return ResponseEntity.ok(candidates);
//    }

    @GetMapping
    @ResponseBody
    public Page<CandidateDTO> getAll(@RequestParam(name = "search", defaultValue = "") String search,
                                     @RequestParam(name = "page", defaultValue = "1") Integer pageIndex,
                                     @RequestParam(name = "size", defaultValue = "2") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);

        Page<Candidate> page = candidateRepository.getAll("%" + search + "%", pageable);
        Page<CandidateDTO> dtoPage = page.map(x -> {
            CandidateDTO candidateDTO = new CandidateDTO();
            BeanUtils.copyProperties(x, candidateDTO);
            return candidateDTO;

        });
        return dtoPage;
    }

}
