package org.example.backend.repository;

import org.example.backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> getByJobName(String jobName);

    @Query("SELECT j FROM Job j WHERE j.status = 'OPEN'")
    List<Job> findAllOpenJobs();
}
