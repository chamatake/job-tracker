package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;

import java.time.LocalDate;
import java.util.Set;

public record JobApplicationDTO(
        Long id,
        JobPostingDTO jobPosting,
        Set<ApplicationStatusDTO> applicationStatuses,
        ApplicationStatusType currentStatusType,
        LocalDate appliedDate,
        String resumeFilename,
        String coverLetterFilename
) {}
