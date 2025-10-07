package bwg.jobTracker.job_tracker.dto;

import java.time.LocalDate;
import java.util.List;

public record JobApplicationDTO(
        Long id,
        JobPostingDTO jobPosting,
        List<ApplicationStatusDTO> applicationStatuses,
        Long currentStatusId,
        LocalDate appliedDate,
        String resumeFilename,
        String coverLetterFilename
) {}
