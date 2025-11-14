package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.enums.OfficeSituation;

public record JobPostingDTO(
        Long id,
        CompanyDTO company,
        String title,
        String requisitionId,
        String url,
        Integer salaryRangeMin,
        Integer salaryRangeMax,
        OfficeSituation officeSituation,
        ReferralSourceDTO referralSource
) {}
