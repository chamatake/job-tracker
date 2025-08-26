package bwg.jobTracker.job_tracker.repository;

import bwg.jobTracker.job_tracker.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findAllByInterviewDate(LocalDate interviewDate);
}
