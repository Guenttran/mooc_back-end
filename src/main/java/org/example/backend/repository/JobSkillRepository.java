package org.example.backend.repository;

import org.example.backend.entity.Job;
import org.example.backend.entity.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    void deleteByJob(Job job);
}
