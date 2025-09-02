package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.JobPostingCreateRequest;
import bwg.jobTracker.job_tracker.dto.JobPostingDTO;
import bwg.jobTracker.job_tracker.service.JobPostingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-postings")
public class JobPostingController {
    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService service) {
        this.jobPostingService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobPostingDTO create(@RequestBody JobPostingCreateRequest request) {
        return this.jobPostingService.add(request);
    }

    @GetMapping
    public List<JobPostingDTO> getAll() {
        return this.jobPostingService.findAll();
    }

    @GetMapping("/{id}")
    public JobPostingDTO getById(@PathVariable Long id) {
        return this.jobPostingService.findById(id);
    }

    @GetMapping(path = "/company", params = "companyName")
    public List<JobPostingDTO> getAllByCompanyName(@RequestParam String companyName) {
        return this.jobPostingService.findAllByCompanyName(companyName);
    }
}
