package bwg.jobTracker.job_tracker.dto.request;

import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReferralSourceCreateRequest {
    private String name;
    private ReferralSourceType referralSourceType;
}
