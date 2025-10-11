package bwg.jobTracker.job_tracker.repository;

import bwg.jobTracker.job_tracker.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
    List<ApplicationStatus> findAllByJobApplication_Id(Long jobApplicationId);
    List<ApplicationStatus> findAllByApplicationStatusType(String applicationStatusType);
}
