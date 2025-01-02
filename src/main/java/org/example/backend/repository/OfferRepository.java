package org.example.backend.repository;

import org.example.backend.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query(value = "from Offer o join o.register r join r.candidate c join c.recruiter e " +
            "where lower(c.fullname) like lower(:search)" +
            "or lower(c.email) like lower(:search)" +
            "or lower(o.approve.firstName) like lower(:search)" +
            "or lower(o.approve.lastName) like lower(:search)")
    Page<Offer> findBy(String search, Pageable pageable);


}
