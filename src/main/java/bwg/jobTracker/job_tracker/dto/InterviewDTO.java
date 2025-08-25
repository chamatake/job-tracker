package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.entity.JobApplication;
import bwg.jobTracker.job_tracker.enums.InterviewType;

import java.time.LocalDate;

public record InterviewDTO(
        Long id,
        JobApplication jobApplication,
        InterviewType interviewType,
        LocalDate interviewDate,
        String notes
) {}
