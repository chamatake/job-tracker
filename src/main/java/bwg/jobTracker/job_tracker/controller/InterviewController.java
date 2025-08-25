package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.InterviewCreateRequest;
import bwg.jobTracker.job_tracker.dto.InterviewDTO;
import bwg.jobTracker.job_tracker.service.InterviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewDTO add(@RequestBody InterviewCreateRequest request) {
        return this.interviewService.add(request);
    }

    @GetMapping
    public List<InterviewDTO> findAll() {
        return this.interviewService.findAll();
    }

    @GetMapping("/{interviewDate}")
    public List<InterviewDTO> findAllByInterviewDate(@RequestParam String dateString) {
        return this.interviewService.findAllByInterviewDate(dateString);
    }

}
