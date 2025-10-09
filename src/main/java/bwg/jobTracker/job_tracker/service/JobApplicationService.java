package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.request.JobApplicationCreateRequest;
import bwg.jobTracker.job_tracker.dto.JobApplicationDTO;
import bwg.jobTracker.job_tracker.dto.request.JobApplicationUpdateStatusRequest;
import bwg.jobTracker.job_tracker.entity.ApplicationStatus;
import bwg.jobTracker.job_tracker.entity.JobApplication;
import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import bwg.jobTracker.job_tracker.exception.JobApplicationNotFoundException;
import bwg.jobTracker.job_tracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
                .withAppliedDate(now)
                .withResumeFilename(request.getResumeFilename())
                .withCoverLetterFileName(request.getCoverLetterFilename())
                .build();

        initialStatus.setJobApplication(application);
        application.setApplicationStatuses(List.of(initialStatus));
        application.setCurrentStatus(initialStatus);

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

    @Transactional
    public JobApplicationDTO updateStatusById(JobApplicationUpdateStatusRequest request) {
        JobApplication existing = this.jobApplicationRepository.findById(request.getJobApplicationId())
                .orElseThrow(() -> new JobApplicationNotFoundException("No job application exists for id = " + request.getJobApplicationId()));

        existing.updateStatus(request.getStatusType());
        JobApplication updated = this.jobApplicationRepository.save(existing);

        return MapperUtil.toJobApplicationDTO(updated);
    }
}
