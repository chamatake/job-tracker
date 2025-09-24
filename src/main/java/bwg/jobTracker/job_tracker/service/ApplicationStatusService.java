package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.request.ApplicationStatusCreateRequest;
import bwg.jobTracker.job_tracker.dto.ApplicationStatusDTO;
import bwg.jobTracker.job_tracker.entity.ApplicationStatus;
import bwg.jobTracker.job_tracker.repository.ApplicationStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationStatusService {
    private final ApplicationStatusRepository applicationStatusRepository;

    public ApplicationStatusService(ApplicationStatusRepository applicationStatusRepository) {
        this.applicationStatusRepository = applicationStatusRepository;
    }

    public ApplicationStatusDTO add(ApplicationStatusCreateRequest request) {
        ApplicationStatus applicationStatus = new ApplicationStatus(request.getApplicationStatusType());
        applicationStatus.setActiveDate(request.getActiveDate() != null ?
                request.getActiveDate() : LocalDate.now());

        ApplicationStatus added = this.applicationStatusRepository.save(applicationStatus);
        return MapperUtil.toApplicationStatusDTO(added);
    }

    public List<ApplicationStatusDTO> findAllByJobApplicationId(Long jobApplicationId) {
        return this.applicationStatusRepository.findAllByJobApplication_Id(jobApplicationId).stream()
                .map(MapperUtil::toApplicationStatusDTO)
                .toList();
    }

}
