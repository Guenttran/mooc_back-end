package org.example.backend.repository;

import org.example.backend.entity.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<InterviewSchedule, Long> {

}
