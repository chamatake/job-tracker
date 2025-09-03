package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.request.JobApplicationCreateRequest;
import bwg.jobTracker.job_tracker.dto.JobApplicationDTO;
import bwg.jobTracker.job_tracker.entity.ApplicationStatus;
import bwg.jobTracker.job_tracker.entity.JobApplication;
import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import bwg.jobTracker.job_tracker.exception.JobApplicationNotFoundException;
import bwg.jobTracker.job_tracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(JobApplicationRepository applicationRepository) {
        this.jobApplicationRepository = applicationRepository;
    }

    public JobApplicationDTO add(JobApplicationCreateRequest request) {
        LocalDate now = LocalDate.now();
        ApplicationStatus initialStatus = new ApplicationStatus();

        initialStatus.setApplicationStatusType(ApplicationStatusType.APPLIED);
        initialStatus.setActiveDate(now);

        JobApplication application = new JobApplication.Builder()
                .withJobPosting(request.getJobPosting())
                .withApplicationStatuses(Set.of(initialStatus))
                .withCurrentStatusType(initialStatus.getApplicationStatusType())
                .withAppliedDate(now)
                .withResumeFilename(request.getResumeFilename())
                .withCoverLetterFileName(request.getCoverLetterFilename())
                .build();
        JobApplication added = this.jobApplicationRepository.save(application);

        return MapperUtil.toJobApplicationDTO(added);
    }

    public List<JobApplicationDTO> findAll() {
        return this.jobApplicationRepository.findAll().stream()
                .map(MapperUtil::toJobApplicationDTO)
                .toList();
    }

    public JobApplicationDTO findById(Long id) {
        JobApplication existing = this.jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException("No job application exists for id = " + id));

        return MapperUtil.toJobApplicationDTO(existing);
    }

    public JobApplicationDTO updateStatusById(Long jobApplicationId, ApplicationStatusType statusType) {
        JobApplication existing = this.jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new JobApplicationNotFoundException("No job application exists for id = " + jobApplicationId));

        ApplicationStatus updatedStatus = new ApplicationStatus(statusType);
        updatedStatus.setJobApplication(existing);
        updatedStatus.setActiveDate(LocalDate.now());
        existing.updateStatus(updatedStatus);

        return MapperUtil.toJobApplicationDTO(this.jobApplicationRepository.save(existing));

    }
}
