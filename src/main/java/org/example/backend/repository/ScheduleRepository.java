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
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<InterviewSchedule, Long> {

    @Query(value = "from InterviewSchedule s join s.register r join r.job j join r.candidate c where lower(j.jobName) like lower(:search)" +
            " or lower(c.fullname) like lower(:search)")
    Page<InterviewSchedule> findBy(String search,Pageable pageable);

    @Query(value = "from InterviewSchedule s join s.register r join r.job j join j.employee e where e.employeeId = :employeeId")
    Page<InterviewSchedule> findAllByEmployeeId(Long employeeId, Pageable pageable);

    Optional<InterviewSchedule> getByScheduleTitle(String scheduleTitle);
}
