package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.enums.InterviewType;

import java.time.LocalDate;

public record InterviewDTO(
        Long id,
        JobApplicationDTO jobApplication,
        InterviewType interviewType,
        LocalDate interviewDate,
        String notes
) {}
