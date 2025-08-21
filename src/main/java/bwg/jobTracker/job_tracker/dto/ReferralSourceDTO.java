package bwg.jobTracker.job_tracker.dto;

import bwg.jobTracker.job_tracker.enums.ReferralSourceType;

public record ReferralSourceDTO(
        Long id,
        String name,
        ReferralSourceType referralSourceType
) {}
