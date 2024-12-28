package org.example.backend.repository;

import org.example.backend.entity.InterviewSchedule;
import org.example.backend.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {

    @Query(value = "from Register r join r.candidate c join r.job j where lower(c.fullname) like lower(:candidateName) and j.jobName like :job")
    Optional<Register> findByCandidateFullNameAndJobName(String candidateName, String job);

    Optional<Register> getByInterviewSchedule(InterviewSchedule schedule);
}
