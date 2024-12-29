package org.example.backend.controller;

import org.example.backend.dto.employee.CandidateDTO;
import org.example.backend.entity.Candidate;
import org.example.backend.repository.CandidateRepository;
import org.example.backend.service.candidate.CandidateService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/candidates")
@CrossOrigin("*")
public class CandidateController {

    private final CandidateService candidateService;
    private final CandidateRepository candidateRepository;

    public CandidateController(CandidateService candidateService, CandidateRepository candidateRepository) {
        this.candidateService = candidateService;
        this.candidateRepository = candidateRepository;
    }

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

    @PostMapping
    public ResponseEntity<?> saveCandidate(@Validated @ModelAttribute CandidateDTO candidateDTO,
                                           BindingResult bindingResult,
                                           @RequestParam("cv") MultipartFile cv) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            candidateService.saveWithCv(candidateDTO, cv);
            return ResponseEntity.ok("Candidate created successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading CV file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id,
                                             @Validated @ModelAttribute CandidateDTO candidateDTO,
                                             BindingResult bindingResult,
                                             @RequestParam("cv") MultipartFile cv) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (cv.isEmpty() || !cv.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Invalid CV file! Only PDF files are allowed.");
        }

        try {
            candidateService.update(id, candidateDTO, cv);
            return ResponseEntity.ok("Candidate updated successfully!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCandidate(@PathVariable Long id) {
        candidateRepository.deleteById(id);
    }

}
