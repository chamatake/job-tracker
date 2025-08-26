package bwg.jobTracker.job_tracker.repository;

import bwg.jobTracker.job_tracker.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
/*    @Query("")
    List<JobApplication> findAllActive();*/
}
