package org.example.backend.repository;

import org.example.backend.entity.Job;
import org.example.backend.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "FROM Job j WHERE lower(j.jobName) LIKE lower(:search)")
    Page<Job> getBy(Pageable pageable, String search);

    @Query(value = "select s from JobSkill js join js.job j join js.skill s where j.jobId = :jobId")
    List<Skill> getSkillsByJobId(Long jobId);
}
