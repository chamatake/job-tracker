package bwg.jobTracker.job_tracker.repository;

import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    Optional<List<JobPosting>> findAllByCompany(Company company);
}
