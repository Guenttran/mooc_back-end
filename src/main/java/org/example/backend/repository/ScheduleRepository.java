package org.example.backend.repository;

import org.example.backend.dto.schedule.InterviewScheduleDTO;
import org.example.backend.entity.InterviewSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<InterviewSchedule, Long> {

    @Query(value = "from InterviewSchedule s join s.register r join r.job j join r.candidate c where j.jobName like :search" +
            " or lower(c.fullname) like lower(:search)")
    Page<InterviewSchedule> findBy(String search,Pageable pageable);

}
