package org.example.backend.service.offer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.offer.OfferRequestDTO;
import org.example.backend.dto.offer.OfferResponseDTO;
import org.example.backend.entity.Candidate;
import org.example.backend.entity.Offer;
import org.example.backend.entity.Register;
import org.example.backend.repository.*;
import org.example.backend.utils.enums.CandidateStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final RegisterRepository registerRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public Page<OfferRequestDTO> getAllOffers(Pageable pageable, String search) {
        Page<Offer> page = offerRepository.findBy("%" + search + "%", pageable);
        return page.map(this::convertToDTO);
    }

    public void createOffer(OfferResponseDTO offerDTO) {
        Offer offer = new Offer();
        BeanUtils.copyProperties(offerDTO, offer);
        Register register = dataEdit(offerDTO);
        offer.setApprove(employeeRepository.getByFullName(offerDTO.getApprove())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found")));
        offer.setRegister(register);
        offer.setStatus("WAITING_FOR_APPROVAL");
        Candidate candidate = register.getCandidate();
        candidate.setStatus(CandidateStatus.WAITING_FOR_APPROVAL);
        candidateRepository.save(candidate);
        offerRepository.save(offer);
    }

    public void updateOffer(Long id, OfferResponseDTO offerDTO) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found"));
        BeanUtils.copyProperties(offerDTO, offer);
        Register register = dataEdit(offerDTO);
        offer.setApprove(employeeRepository.getByFullName(offerDTO.getApprove())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found")));
        offer.setRegister(register);
        offerRepository.save(offer);
    }

    public void updateStatus(Long id, String status) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found"));
        offer.setStatus(status);
        offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found"));
        offerRepository.delete(offer);
    }

    private OfferRequestDTO convertToDTO(Offer offer) {
        OfferRequestDTO dto = new OfferRequestDTO();
        dto.setOfferId(offer.getOfferId());
        dto.setCandidateName(offer.getRegister().getCandidate().getFullname());
        dto.setCandidateEmail(offer.getRegister().getCandidate().getEmail());
        dto.setApprove(offer.getApprove().getFirstName() + " " + offer.getApprove().getLastName());
        dto.setDepartment(offer.getRegister().getJob().getEmployee().getDepartment().getName());
        dto.setStatus(offer.getStatus());
        return dto;
    }

    private Register dataEdit(OfferResponseDTO offerResponseDTO){

        return registerRepository.getByInterviewSchedule(scheduleRepository.
                        getByScheduleTitle(offerResponseDTO.getInterviewTitle())
                        .orElseThrow(() -> new EntityNotFoundException("Schedule not found")))
                .orElseThrow(() -> new EntityNotFoundException("Register not found"));
    }

}
