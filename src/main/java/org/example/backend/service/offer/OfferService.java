package org.example.backend.service.offer;

import org.example.backend.dto.offer.OfferRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {
    Page<OfferRequestDTO> getAllOffers(Pageable pageable, String search);
}
