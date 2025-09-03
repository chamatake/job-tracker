package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.request.InterviewCreateRequest;
import bwg.jobTracker.job_tracker.dto.InterviewDTO;
import bwg.jobTracker.job_tracker.service.InterviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewDTO create(@RequestBody InterviewCreateRequest request) {
        return this.interviewService.add(request);
    }

    @GetMapping
    public List<InterviewDTO> getAll() {
        return this.interviewService.findAll();
    }

    @GetMapping(params = "interviewDate")
    public List<InterviewDTO> getAllByInterviewDate(@RequestParam String interviewDate) {
        return this.interviewService.findAllByInterviewDate(interviewDate);
    }

}
