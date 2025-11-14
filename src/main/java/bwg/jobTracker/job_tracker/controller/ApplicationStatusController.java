package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.ApplicationStatusDTO;
import bwg.jobTracker.job_tracker.dto.request.ApplicationStatusCreateRequest;
import bwg.jobTracker.job_tracker.service.ApplicationStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-statuses")
public class ApplicationStatusController {
    private final ApplicationStatusService applicationStatusService;

    public ApplicationStatusController(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationStatusDTO create(@RequestBody ApplicationStatusCreateRequest request) {
        return this.applicationStatusService.add(request);
    }

    @GetMapping("/job-application/{id}")
    public List<ApplicationStatusDTO> getAllByJobApplicationId(@PathVariable Long applicationId) {
        return this.applicationStatusService.findAllByJobApplicationId(applicationId);
    }

    @GetMapping(path = "/status-type", params = "statusType")
    public List<ApplicationStatusDTO> getAllByStatusType(@RequestParam String statusType) {
        return this.applicationStatusService.findAllByStatusType(statusType);
    }

}
