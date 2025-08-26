package bwg.jobTracker.job_tracker.dto;

import java.time.LocalDate;
import java.util.Set;

public record JobApplicationDTO(
        Long id,
        JobPostingDTO jobPosting,
        Set<ApplicationStatusDTO> applicationStatuses,
        ApplicationStatusDTO currentStatus,
        LocalDate appliedDate,
        String resumeFilename,
        String coverLetterFilename
) {}
