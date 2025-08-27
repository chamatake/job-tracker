package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;

import java.time.LocalDate;

public record ApplicationStatusDTO(
        Long id,
        JobApplicationDTO jobApplication,
        ApplicationStatusType applicationStatusType,
        LocalDate activeDate
) {}
