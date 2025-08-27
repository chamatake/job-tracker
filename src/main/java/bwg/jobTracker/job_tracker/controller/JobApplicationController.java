package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.JobApplicationCreateRequest;
import bwg.jobTracker.job_tracker.dto.JobApplicationDTO;
import bwg.jobTracker.job_tracker.entity.JobApplication;
import bwg.jobTracker.job_tracker.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobApplication")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService applicationService) {
        this.jobApplicationService = applicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplicationDTO add(JobApplicationCreateRequest request) {
        return this.jobApplicationService.add(request);

    }

    @GetMapping("/{id}")
    public JobApplicationDTO findById(@PathVariable Long id) {
        return this.jobApplicationService.findById(id);
    }

    @GetMapping
    public List<JobApplicationDTO> findAll() {
        return this.jobApplicationService.findAll();
    }

    @PutMapping("/{id}/update/{applicationStatusType}")
    public JobApplicationDTO updateStatusById(@PathVariable Long id, @PathVariable String applicationStatusType) {
        return this.jobApplicationService.updateStatusById(id, applicationStatusType);
    }
}
