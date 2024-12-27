package org.example.backend.repository;

import org.example.backend.dto.employee.CandidateDTO;
import org.example.backend.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(value = " from Candidate c where lower(c.fullname) like lower(:search)" +
            " or lower(c.email) like lower(:search)" +
            " or lower(c.phone) like lower(:search) ")
    Page<Candidate> getAll(String search, Pageable pageable);

}
