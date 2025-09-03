package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.request.ApplicationStatusUpdateRequest;
import bwg.jobTracker.job_tracker.dto.request.JobApplicationCreateRequest;
import bwg.jobTracker.job_tracker.dto.JobApplicationDTO;
import bwg.jobTracker.job_tracker.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService applicationService) {
        this.jobApplicationService = applicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationDTO create(JobApplicationCreateRequest request) {
        return this.jobApplicationService.add(request);

    }

    @GetMapping("/{id}")
    public JobApplicationDTO getById(@PathVariable Long id) {
        return this.jobApplicationService.findById(id);
    }

    @GetMapping
    public List<JobApplicationDTO> getAll() {
        return this.jobApplicationService.findAll();
    }

    @PatchMapping("/{id}/status")
    public JobApplicationDTO updateStatusTypeById(@PathVariable Long id, @RequestBody ApplicationStatusUpdateRequest request) {
        return this.jobApplicationService.updateStatusById(id, request.getApplicationStatusType());
    }
}
