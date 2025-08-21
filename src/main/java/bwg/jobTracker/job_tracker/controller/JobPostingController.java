package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.dto.JobPostingCreateRequest;
import bwg.jobTracker.job_tracker.dto.JobPostingDTO;
import bwg.jobTracker.job_tracker.service.JobPostingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobPosting")
public class JobPostingController {
    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService service) {
        this.jobPostingService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobPostingDTO add(@RequestBody JobPostingCreateRequest request) {
        return this.jobPostingService.add(request);
    }

    @GetMapping
    public List<JobPostingDTO> findAll() {
        return this.jobPostingService.findAll();
    }

    @GetMapping("/{companyName}")
    public List<JobPostingDTO> findAllByCompanyName(@RequestParam String companyName) {
        return this.jobPostingService.findAllByCompanyName(companyName);
    }
}
