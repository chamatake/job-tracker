package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.Technology;

import java.util.Set;

public record JobPostingDTO(
        Long id,
        CompanyDTO company,
        String title,
        String requisitionId,
        String url,
        Long salaryRangeMin,
        Long salaryRangeMax,
        OfficeSituation officeSituation,
        Set<Technology> requiredTech,
        Set<Technology> preferredTech,
        ReferralSourceDTO referralSource
) {}
