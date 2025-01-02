package org.example.backend.controller;

import lombok.AllArgsConstructor;
import org.example.backend.dto.offer.OfferRequestDTO;
import org.example.backend.dto.offer.OfferResponseDTO;
import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.example.backend.service.offer.OfferServiceImpl;
import org.example.backend.service.schedule.ScheduleServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin("*")
@AllArgsConstructor
public class OfferController {
    private final OfferServiceImpl offerService;
    private final ScheduleServiceImpl scheduleService;

    @GetMapping
    public Page<OfferRequestDTO> getAllOffers(
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return offerService.getAllOffers(pageable, search);
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<Map<String, Object>> createOfferFromSchedule(@PathVariable Long id) {
        InterviewScheduleDTO schedule = scheduleService.getScheduleById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("schedules", schedule);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addOffer(@Validated @RequestBody OfferResponseDTO offer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        offerService.createOffer(offer);
        return ResponseEntity.ok().body("Offer created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(
            @PathVariable Long id,
            @Validated @RequestBody OfferResponseDTO offer,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        offerService.updateOffer(id, offer);
        return ResponseEntity.ok().body("Offer updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.ok().body("Offer deleted successfully!");
    }

}
